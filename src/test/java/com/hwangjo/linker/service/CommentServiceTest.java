package com.hwangjo.linker.service;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hwangjo.linker.config.security.CustomUser;
import com.hwangjo.linker.domain.Board;
import com.hwangjo.linker.domain.Comment;
import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.Member;
import com.hwangjo.linker.dto.BoardRequest;
import com.hwangjo.linker.dto.CommentRequest;
import com.hwangjo.linker.dto.FolderRequest;
import com.hwangjo.linker.dto.RegisterRequest;
import com.hwangjo.linker.repository.MemberRepository;

@SpringBootTest
@Transactional
class CommentServiceTest {

	@Autowired
	private UserService userService;
	@Autowired
	private MemberRepository userRepository;
	@Autowired
	private FolderService folderService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private CommentService commentService;

	private Folder folder;
	private CustomUser user;
	private Board board;

	@BeforeEach
	void 전처리() {
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

		BoardRequest boardRequest = new BoardRequest();
		boardRequest.setTitle("제목");
		boardRequest.setContent("내용");
		boardRequest.setFolderId(folder.getId());

		board = boardService.addNewBoard(user, boardRequest);
	}

	@Test
	void addNewComment() {
		CommentRequest request = new CommentRequest();
		request.setBoardId(board.getId());
		request.setContent("내용");
		Comment comment = commentService.addNewComment(user, request);
		Assertions.assertThat(comment.getMember()).isEqualTo(user.getMember());
	}

	@Test
	@DisplayName("삭제 성공")
	void deleteComment() {
		addNewComment();
		Comment comment = commentService.getComments(board.getId()).get(0);

		commentService.deleteComment(user, comment.getId());

		Assertions.assertThat(commentService.getComments(board.getId()).size()).isEqualTo(0);
	}

	@Test
	@DisplayName("삭제 실패")
	void FailToDeleteComment() {
		addNewComment();
		Comment comment = commentService.getComments(board.getId()).get(0);
		Assertions.assertThatThrownBy(() -> commentService.deleteComment(user, comment.getId() + 1))
			.isInstanceOf(NoSuchElementException.class);
	}

}