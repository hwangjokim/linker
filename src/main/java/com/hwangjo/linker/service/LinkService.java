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
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;
    private final FolderRepository folderRepository;
    private final FolderService folderService;

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

    public void deleteLink(CustomUser user, Long linkId, UUID folderId){
        Link link = validateAndGetLink(user, linkId, folderId);
        linkRepository.delete(link);
    }

    private Link validateAndGetLink(CustomUser user, Long linkId, UUID folderId){
        return folderService.validateAndGetFolder(user, folderId).getLinks()
                .stream()
                .filter(l -> l.getId().equals(linkId)).findFirst().orElseThrow(NoSuchElementException::new);
    }
}
