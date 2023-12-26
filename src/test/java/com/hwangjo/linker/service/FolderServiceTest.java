package com.hwangjo.linker.service;


import java.util.List;
import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.hwangjo.linker.config.security.CustomUser;
import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.Member;
import com.hwangjo.linker.dto.FolderRequest;
import com.hwangjo.linker.repository.MemberRepository;

@SpringBootTest
@Transactional
class FolderServiceTest {

	@Autowired
	private FolderService service;

	@Autowired
	private MemberRepository memberRepository;
	@Test
	void addNewFolder() {
		//give
		Member member = memberRepository.findByUsername("testid").get();
		CustomUser user = new CustomUser(member);
		FolderRequest folderRequest = new FolderRequest();
		folderRequest.setFolderName("newFolder");

		//when
		Folder folder = service.addNewFolder(user, folderRequest);

		//then
		Assertions.assertThat(folder.getFolderName()).isEqualTo("newFolder");
	}


	@Test
	void getAllFolders() {
		Member member = memberRepository.findByUsername("testid").get();
		CustomUser user = new CustomUser(member);

		List<Folder> folders = service.getAllFolders(user);

		Assertions.assertThat(folders.size()).isEqualTo(2);
	}

	@Test
	@Commit
	void renameFolder() {
		Member member = memberRepository.findByUsername("testid").get();
		CustomUser user = new CustomUser(member);
		Folder folder = service.getAllFolders(user)
			.stream()
			.filter(f -> f.getFolderName().equals("TestFolder1"))
			.findFirst()
			.get();

		service.renameFolder(user, folder.getId(), "newName");

	}

	@Test
	@DisplayName("폴더이름변경_실패")
	void failRename(){
		Member member1 = memberRepository.findByUsername("testid").get();
		Member member2 = memberRepository.findByUsername("test1").get();

		CustomUser user1 = new CustomUser(member1);
		CustomUser user2 = new CustomUser(member2);

		Folder folder = service.getAllFolders(user1)
			.stream()
			.filter(f -> f.getFolderName().equals("TestFolder2"))
			.findFirst()
			.get();
		Assertions.assertThatThrownBy(() -> service.renameFolder(user2, folder.getId(), "newName2")).isInstanceOf(NoSuchElementException.class);
	}
}