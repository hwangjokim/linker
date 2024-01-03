package com.hwangjo.linker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hwangjo.linker.domain.Board;
import com.hwangjo.linker.domain.Comment;
import com.hwangjo.linker.domain.Member;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	@Query("select c from Comment c join fetch c.member where c.board = :board order by c.createdAt ASC ")
	List<Comment> findCommentsByBoard(@Param("board") Board board);

	Optional<Comment> findCommentByMemberAndId(Member member, Long id);

}
