package com.hwangjo.linker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hwangjo.linker.domain.Board;
import com.hwangjo.linker.domain.Member;

public interface BoardRepository extends JpaRepository<Board, Long> {

	@Query("select b from Board b left join fetch b.comments join fetch b.member where b.id = :id")
	Optional<Board> findBoardById(@Param("id") Long id);

	@Query("select b from Board b join fetch b.member")
	List<Board> findAllBoard();

	Optional<Board> findByIdAndMember(Long id, Member member);
}
