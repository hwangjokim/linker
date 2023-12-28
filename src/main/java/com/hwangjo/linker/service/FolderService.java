package com.hwangjo.linker.service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.hwangjo.linker.config.security.CustomUser;
import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.Member;
import com.hwangjo.linker.dto.FolderRequest;
import com.hwangjo.linker.repository.FolderRepository;
import com.hwangjo.linker.repository.FolderShareRepository;
import com.hwangjo.linker.repository.LinkRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FolderService {
    private final FolderRepository folderRepository;
    private final LinkRepository linkRepository;
    private final FolderShareRepository shareRepository;

    public Folder addNewFolder(CustomUser user, FolderRequest request){
        checkDuplicate(user.getMember(), request.folderName);
        Folder folder = Folder.builder()
            .folderName(request.folderName)
            .owner(user.getMember())
            .shareStatus(false)
            .build();
        return folderRepository.save(folder);
    }

    private void checkDuplicate(Member member, String request){
        if (folderRepository.existsByFolderNameAndOwner(request, member)) {
            throw new IllegalStateException();
        }
    }

    public List<Folder> getAllFolders(CustomUser user){
        return folderRepository.findAllByOwner(user.getMember());
    }

    public void renameFolder(CustomUser user, UUID folderID, String folderName){
        Folder folder = validateAndGetFolder(user, folderID);
        folder.updateFolderName(folderName);
    }
    public void deleteFolder(CustomUser user, UUID folderId){
        Folder folder = validateAndGetFolder(user, folderId);
        linkRepository.deleteAllByFolder(folder);
        shareRepository.deleteAllByFolder(folder);
        folderRepository.delete(folder);
    }

    public Folder validateAndGetFolder(CustomUser user, UUID folderID) {
        return folderRepository.findByIdAndOwner(folderID, user.getMember()).orElseThrow(() -> new NoSuchElementException("폴더가 존재하지 않거나, 권한이 없습니다."));
    }




}
