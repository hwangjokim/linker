package com.hwangjo.linker.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hwangjo.linker.config.security.CustomUser;
import com.hwangjo.linker.domain.Board;
import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.dto.BoardRequest;
import com.hwangjo.linker.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;
	private final FolderService folderService;

	public Board addNewBoard(CustomUser user, BoardRequest request) {
		Board board = Board.builder()
			.title(request.getTitle())
			.content(request.getContent())
			.createdAt(LocalDateTime.now())
			.member(user.getMember()).build();

		if(request.getFolderId() != null){
			Folder folder = folderService.validateAndGetFolder(user, request.getFolderId());
			board.setFolder(folder);
		}

		return boardRepository.save(board);
	}

	public Board getBoard(Long id) {
		return boardRepository.findBoardById(id).orElseThrow(NoSuchElementException::new);
	}

	public List<Board> getBoardList() {
		return boardRepository.findAllBoard();
	}

	public void updateBoard(CustomUser user, Long id, BoardRequest request) {
		Board board = validateAndGetBoard(user, id);
		board.updateBoard(request.getTitle(), request.getContent());
	}
	public void deleteBoard(CustomUser user ,Long id) {
		//TODO: 댓글기능 추가시, 댓글 먼저 삭제하는 Logic 추가
		boardRepository.delete(validateAndGetBoard(user, id));
	}

	private Board validateAndGetBoard(CustomUser user, Long id) {
		return boardRepository.findByIdAndMember(id, user.getMember()).orElseThrow(NoSuchElementException::new);
	}

}
