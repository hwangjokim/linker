package com.hwangjo.linker.repository;

import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByIdAndFolder_Id(Long linkId, UUID folderId);

    @Query("delete from Link l where l.folder = :folder")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void deleteAllByFolder(@Param("folder") Folder folder);
}
