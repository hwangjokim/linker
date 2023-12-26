package com.hwangjo.linker.controller;

import com.hwangjo.linker.config.security.CustomUser;
import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.dto.FolderRequest;
import com.hwangjo.linker.service.FolderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/link")
public class LinkController {
	private final FolderService service;

	@GetMapping
	public String myLink(@AuthenticationPrincipal CustomUser user,Model model){
		model.addAttribute("folders", service.getAllFolders(user));
		model.addAttribute("request", new FolderRequest());
		return "link";
	}

	@PostMapping
	public String saveLink(@AuthenticationPrincipal CustomUser user, FolderRequest request){
		service.addNewFolder(user, request);
		return "redirect:/link";
	}







}
