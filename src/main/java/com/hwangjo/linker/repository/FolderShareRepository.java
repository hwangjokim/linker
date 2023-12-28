package com.hwangjo.linker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.FolderShare;
import com.hwangjo.linker.domain.Member;

public interface FolderShareRepository extends JpaRepository<FolderShare, Long> {
	@Query("select fs.folder from FolderShare fs where fs.member = :member")
	List<Folder> findSharedFolderByMember(@Param("member") Member member);

	@Query("delete from FolderShare fs where fs.folder = :folder")
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	void deleteAllByFolder(@Param("folder") Folder folder);
}
