package com.hwangjo.linker.service;


import com.hwangjo.linker.config.security.CustomUser;
import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.Link;
import com.hwangjo.linker.dto.LinkRequest;
import com.hwangjo.linker.repository.FolderRepository;
import com.hwangjo.linker.repository.LinkRepository;
import com.hwangjo.linker.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;
    private final FolderRepository folderRepository;

    public Link addNewLink(CustomUser user, LinkRequest request) {
        Folder folder = folderRepository.getReferenceById(request.getFolderId());
        Link link = Link.builder()
                .link(request.getLink())
                .description(request.getDescription())
                .createdAt(LocalDate.now())
                .lastClickedAt(LocalDate.now())
                .folder(folder).build();
        return linkRepository.save(link);
    }
}
