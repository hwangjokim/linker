package com.hwangjo.linker.repository;

import com.hwangjo.linker.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByIdAndFolder_Id(Long linkId, UUID folderId);
}
