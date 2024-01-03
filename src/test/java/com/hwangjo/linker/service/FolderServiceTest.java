package com.hwangjo.linker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hwangjo.linker.config.security.CustomUser;
import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.Member;
import com.hwangjo.linker.dto.FolderRequest;
import com.hwangjo.linker.dto.RegisterRequest;
import com.hwangjo.linker.repository.MemberRepository;

@SpringBootTest
@Transactional
class FolderServiceTest {

	@Autowired
	private FolderService folderService;

	@Autowired
	private MemberRepository userRepository;

	@Autowired
	private UserService userService;

	private CustomUser user;
	private Folder folder;
	@BeforeEach
	void 전처리(){
		//user 및 folder 세팅
		RegisterRequest request = new RegisterRequest();
		request.setUsername("code_test");
		request.setPassword("password!");
		request.setNickname("nick");
		request.setPasswordRepeat("password!");
		ArrayList<String> register = userService.register(request);
		register.forEach(System.out::println);
		Assertions.assertThat(register.size()).isEqualTo(0);

		Member member = userRepository.findByUsername("code_test").get();
		user = new CustomUser(member);
	}
	// @AfterEach
	// void 후처리(){
	// 	folderService.deleteFolder(user, folder.getId());
	// 	userRepository.delete(user.getMember());
	// }
	@Test
	void addNewFolder() {
		//give
		FolderRequest folderRequest = new FolderRequest();
		folderRequest.setFolderName("TestFolder1");

		//when
		Folder folder = folderService.addNewFolder(user, folderRequest);

		//then
		Assertions.assertThat(folder.getFolderName()).isEqualTo("TestFolder1");
	}


	@Test
	void getAllFolders() {

		addNewFolder();
		List<Folder> folders = folderService.getAllFolders(user);

		Assertions.assertThat(folders.size()).isEqualTo(1);
	}

	@Test
	void renameFolder() {
		addNewFolder();

		Folder folder = folderService.getAllFolders(user)
			.stream()
			.filter(f -> f.getFolderName().equals("TestFolder1"))
			.findFirst()
			.get();

		folderService.renameFolder(user, folder.getId(), "newName");

	}

	@Test
	@DisplayName("폴더이름변경_실패")
	void failRename(){
		addNewFolder();
		add_second_user();
		Member member1 = userRepository.findByUsername("code_test").get();
		Member member2 = userRepository.findByUsername("code_test_2").get();

		CustomUser user1 = new CustomUser(member1);
		CustomUser user2 = new CustomUser(member2);

		Folder folder = folderService.getAllFolders(user1)
			.stream()
			.filter(f -> f.getFolderName().equals("TestFolder1"))
			.findFirst()
			.get();
		Assertions.assertThatThrownBy(() -> folderService.renameFolder(user2, folder.getId(), "newName2")).isInstanceOf(NoSuchElementException.class);
	}

	private void add_second_user() {
		RegisterRequest request = new RegisterRequest();
		request.setUsername("code_test_2");
		request.setPassword("password!");
		request.setNickname("nick");
		request.setPasswordRepeat("password!");
		ArrayList<String> register = userService.register(request);
	}
}