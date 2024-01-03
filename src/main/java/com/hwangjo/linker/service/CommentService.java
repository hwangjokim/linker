package com.hwangjo.linker.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hwangjo.linker.config.security.CustomUser;
import com.hwangjo.linker.domain.Comment;
import com.hwangjo.linker.dto.CommentRequest;
import com.hwangjo.linker.repository.BoardRepository;
import com.hwangjo.linker.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;

	public Comment addNewComment(CustomUser user, CommentRequest request) {
		Comment comment = Comment.builder()
			.content(request.getContent())
			.board(boardRepository.findBoardById(request.getBoardId()).orElseThrow(NoSuchElementException::new))
			.member(user.getMember())
			.createdAt(LocalDateTime.now())
			.build();
		return commentRepository.save(comment);
	}

	public void deleteComment(CustomUser user, Long commentId) {
		Comment comment = commentRepository.findCommentByMemberAndId(user.getMember(), commentId)
			.orElseThrow(NoSuchElementException::new);
		commentRepository.delete(comment);
	}

	public List<Comment> getComments(Long boardId) {
		return commentRepository.findCommentsByBoard(
			boardRepository.getReferenceById(boardId));
	}
}
