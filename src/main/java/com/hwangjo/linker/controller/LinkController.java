package com.hwangjo.linker.controller;

import com.hwangjo.linker.config.security.CustomUser;
import com.hwangjo.linker.domain.Link;
import com.hwangjo.linker.dto.FolderRequest;
import com.hwangjo.linker.dto.LinkRequest;
import com.hwangjo.linker.service.FolderService;
import com.hwangjo.linker.service.LinkService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/link")
public class LinkController {
	private final FolderService folderService;
	private final LinkService linkService;

	@GetMapping
	public String myLink(@AuthenticationPrincipal CustomUser user,Model model){
		model.addAttribute("folders", folderService.getAllFolders(user));
		model.addAttribute("FolderRequest", new FolderRequest());
		model.addAttribute("LinkRequest", new LinkRequest());
		return "link";
	}

	@PostMapping
	public String saveFolder(@AuthenticationPrincipal CustomUser user, FolderRequest request){
		folderService.addNewFolder(user, request);
		return "redirect:/link";
	}

	@GetMapping("/delete/{folderId}")
	public String deleteFolder(@AuthenticationPrincipal CustomUser user, @PathVariable("folderId") UUID folderId) {
		folderService.deleteFolder(user, folderId);
		return "redirect:/link";
	}

	@PostMapping("/add")
	public String saveLink(@AuthenticationPrincipal CustomUser user, LinkRequest request) {
		log.info("request = {}", request);
		linkService.addNewLink(user, request);
		return "redirect:/link";
	}

	@GetMapping("/delete/{folderId}/{linkId}")
	public String deleteLink(@AuthenticationPrincipal CustomUser user,
							 @PathVariable("folderId") UUID folderId,
							 @PathVariable("linkId") Long linkId) {
		linkService.deleteLink(user, linkId, folderId);
		return "redirect:/link";
	}


}
