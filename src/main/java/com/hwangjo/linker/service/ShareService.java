package com.hwangjo.linker.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hwangjo.linker.config.security.CustomUser;
import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.FolderShare;
import com.hwangjo.linker.dto.ShareRequest;
import com.hwangjo.linker.dto.SharedFolderResponse;
import com.hwangjo.linker.dto.UserShareRequest;
import com.hwangjo.linker.repository.FolderRepository;
import com.hwangjo.linker.repository.FolderShareRepository;
import com.hwangjo.linker.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ShareService {
	private final FolderService service;
	private final FolderRepository folderRepository;
	private final MemberRepository userRepository;
	private final FolderShareRepository shareRepository;

	public void shareFolderLink(CustomUser user, ShareRequest request) {
		Folder folder = service.validateAndGetFolder(user, request.getFolderId());
		folder.updateShareStatus(request.getShareStatus());
	}

	public SharedFolderResponse getLinkSharedFolder(UUID folderId) {
		Folder folder = folderRepository.findById(folderId).orElseThrow(NoSuchElementException::new);
		if (!folder.getShareStatus())
			throw new IllegalStateException();

		return SharedFolderResponse.from(folder);
	}

	public void shareFolderToUser(CustomUser user, UserShareRequest request) {
		Folder folder = service.validateAndGetFolder(user, request.getFolderId());
		FolderShare share = FolderShare.builder()
			.folder(folder)
			.member(userRepository.findByUsername(request.getUsername()).orElseThrow(NoSuchElementException::new))
			.build();
		shareRepository.save(share);
	}

	public List<Folder> getAllUserSharedFolder(CustomUser user) {
		return shareRepository.findSharedFolderByMember(user.getMember());
	}

}
