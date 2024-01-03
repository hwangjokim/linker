package com.hwangjo.linker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hwangjo.linker.domain.Board;
import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.Member;

public interface BoardRepository extends JpaRepository<Board, Long> {

	@Query("select b from Board b join fetch b.member where b.id = :id")
	Optional<Board> findBoardById(@Param("id") Long id);

	@Query("select b from Board b join fetch b.member")
	List<Board> findAllBoard();

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("update Board b set b.folder = null where b.folder = :folder")
	void updateFolderToNull(@Param("folder") Folder folder);

	Optional<Board> findByIdAndMember(Long id, Member member);
}
