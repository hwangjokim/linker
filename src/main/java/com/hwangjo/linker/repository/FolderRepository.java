package com.hwangjo.linker.repository;

import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.Link;
import com.hwangjo.linker.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FolderRepository extends JpaRepository<Folder, UUID> {
    List<Folder> findAllByOwner(@Param("member") Member member);

    boolean existsByFolderNameAndOwner(String folderName, Member member);

    Optional<Folder> findByIdAndOwner(UUID id, Member member);




}
