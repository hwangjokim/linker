package com.hwangjo.linker.repository;

import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FolderRepository extends JpaRepository<Folder, UUID> {
    List<Folder> findAllByOwner(Member member);

    boolean existsByFolderNameAndOwner(String folderName, Member member);

    Optional<Folder> findByIdAndOwner(UUID id, Member member);
}
