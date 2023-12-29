package com.hwangjo.linker.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hwangjo.linker.config.security.CustomUser;
import com.hwangjo.linker.dto.BoardRequest;
import com.hwangjo.linker.service.BoardService;
import com.hwangjo.linker.service.FolderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

	private final BoardService boardService;
	private final FolderService folderService;

	@GetMapping
	public String boardList(Model model) {
		model.addAttribute("posts", boardService.getBoardList());
		return "board";
	}

	@GetMapping("/create")
	public String createBoard(@AuthenticationPrincipal CustomUser user, Model model) {
		model.addAttribute("folders", folderService.getAllFolders(user));
		model.addAttribute("request", new BoardRequest());
		return "board_create";
	}

	@PostMapping("/create")
	public String createBoard(@AuthenticationPrincipal CustomUser user, BoardRequest request) {
		boardService.addNewBoard(user, request);
		return "redirect:/board";

	}

}
