package com.hwangjo.linker.repository;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hwangjo.linker.config.security.CustomUser;
import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.Member;
import com.hwangjo.linker.dto.FolderRequest;
import com.hwangjo.linker.dto.RegisterRequest;
import com.hwangjo.linker.service.FolderService;
import com.hwangjo.linker.service.UserService;

@SpringBootTest
@Transactional
class FolderRepositoryTest {

	@Autowired
	private MemberRepository userRepository;
	@Autowired
	private FolderRepository folderRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private FolderService folderService;

	@BeforeEach
	void 전처리() {
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
		CustomUser user = new CustomUser(member);

		FolderRequest folderRequest = new FolderRequest();
		folderRequest.setFolderName("myfolder");
		folderService.addNewFolder(user, folderRequest);

	}

	@Test
	void findAllByOwner() {
		Member member = userRepository.findByUsername("code_test").get();
		List<Folder> all = folderRepository.findAllByOwner(member);
		Assertions.assertThat(all.size()).isEqualTo(1);
	}

	@Test
	void save() {
		Member member = userRepository.findByUsername("code_test").get();
		Folder testFolder1 = Folder.builder()
			.folderName("TestFolder2")
			.shareStatus(false)
			.owner(member).build();
		Folder save = folderRepository.save(testFolder1);

		Assertions.assertThat(save).isEqualTo(testFolder1);

	}
}