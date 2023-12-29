package com.hwangjo.linker.service;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hwangjo.linker.config.security.CustomUser;
import com.hwangjo.linker.domain.Board;
import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.Member;
import com.hwangjo.linker.dto.BoardRequest;
import com.hwangjo.linker.dto.FolderRequest;
import com.hwangjo.linker.dto.RegisterRequest;
import com.hwangjo.linker.repository.FolderRepository;
import com.hwangjo.linker.repository.MemberRepository;

@SpringBootTest
@Transactional
class BoardServiceTest {

		CustomUser user;
		Folder folder;

	@Autowired
	private BoardService service;

	@Autowired
	private FolderService folderService;
	@Autowired
	private UserService userService;

	@Autowired
	private MemberRepository userRepository;

	@Autowired
	FolderRepository folderRepository;

	@Test
	void addNewBoard() {

		//give
		BoardRequest boardRequest = new BoardRequest();
		boardRequest.setTitle("제목");
		boardRequest.setContent("내용");
		boardRequest.setFolderId(folder.getId());

		//when
		Board board = service.addNewBoard(user, boardRequest);

		Assertions.assertThat(boardRequest.getTitle()).isEqualTo(board.getTitle());
		Assertions.assertThat(boardRequest.getContent()).isEqualTo(board.getContent());
		Assertions.assertThat(folderRepository.findById(boardRequest.getFolderId()).get()).isEqualTo(board.getFolder());
	}

	@Test
	void getBoard() {

	}

	@Test
	void deleteBoard() {
		//give
		BoardRequest boardRequest = new BoardRequest();
		boardRequest.setTitle("제목");
		boardRequest.setContent("내용");
		boardRequest.setFolderId(folder.getId());
		Board board = service.addNewBoard(user, boardRequest);
		//when
		service.deleteBoard(user, board.getId());
		//then
		Assertions.assertThatThrownBy(() -> service.getBoard(board.getId()))
			.isInstanceOf(NoSuchElementException.class);
	}

	@BeforeEach
	void 전처리(){
		//user 및 folder 세팅
		RegisterRequest request = new RegisterRequest();
		request.setUsername("linktest");
		request.setPassword("password!");
		request.setNickname("nick");
		request.setPasswordRepeat("password!");
		ArrayList<String> register = userService.register(request);
		register.forEach(System.out::println);
		Assertions.assertThat(register.size()).isEqualTo(0);


		Member member = userRepository.findByUsername("linktest").get();
		this.user = new CustomUser(member);

		FolderRequest folderRequest = new FolderRequest();
		folderRequest.setFolderName("myfolder");
		this.folder = folderService.addNewFolder(user, folderRequest);

	}
}