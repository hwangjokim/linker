package com.hwangjo.linker.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hwangjo.linker.dto.LoginRequest;
import com.hwangjo.linker.dto.RegisterRequest;
import com.hwangjo.linker.service.UserService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {
	private final UserService service;

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("request", new LoginRequest());
		return "login";
	}

	@GetMapping("/register")
	public String register(Model model){
		model.addAttribute("request",new RegisterRequest());
		model.addAttribute("errors",new ArrayList<String >());
		return "register";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute("request") RegisterRequest request, Model model){
		ArrayList<String> errorList = service.register(request);
		if (!errorList.isEmpty()){
			model.addAttribute("errors", errorList);
			return "register";
		}
		return "redirect:/login";
	}
}
