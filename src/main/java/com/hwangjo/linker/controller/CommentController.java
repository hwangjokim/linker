package com.hwangjo.linker.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hwangjo.linker.config.security.CustomUser;
import com.hwangjo.linker.dto.CommentRequest;
import com.hwangjo.linker.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/comment")
@Slf4j
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@PostMapping
	public String addComment(@AuthenticationPrincipal CustomUser user, CommentRequest commentRequest,
		RedirectAttributes redirectAttributes) {

		System.out.println("commentRequest = " + commentRequest);
		commentService.addNewComment(user, commentRequest);
		redirectAttributes.addAttribute("boardId", commentRequest.getBoardId());

		return "redirect:/board/{boardId}";
	}

	@GetMapping("/{boardId}/{commentId}")
	public String deleteComment(@AuthenticationPrincipal CustomUser user, @PathVariable("boardId") Long boardId,
		@PathVariable("commentId") Long commentId, RedirectAttributes redirectAttributes) {

		commentService.deleteComment(user, commentId);
		redirectAttributes.addAttribute("boardId", boardId);
		return "redirect:/board/{boardId}";
	}
}
