package com.hwangjo.linker.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.Member;

@Repository
public interface FolderRepository extends JpaRepository<Folder, UUID> {
	@Query("select f from Folder f left join fetch f.links where f.owner = :member")
	List<Folder> findAllByOwner(@Param("member") Member member);

	boolean existsByFolderNameAndOwner(String folderName, Member member);

	Optional<Folder> findByIdAndOwner(UUID id, Member member);

}
