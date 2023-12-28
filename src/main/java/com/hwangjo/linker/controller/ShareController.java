package com.hwangjo.linker.controller;


import com.hwangjo.linker.config.security.CustomUser;
import com.hwangjo.linker.dto.ShareRequest;
import com.hwangjo.linker.dto.SharedFolderResponse;
import com.hwangjo.linker.dto.UserShareRequest;
import com.hwangjo.linker.service.ShareService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/share")
public class ShareController {
    private final ShareService service;

    @GetMapping("/{folderId}")
    public String getShareFolder(@PathVariable("folderId") UUID folderId, Model model) {
        SharedFolderResponse folder = service.getLinkSharedFolder(folderId);
        model.addAttribute("folder", folder);
        return "share";
    }

    @PostMapping("/link")
    public String shareLink(@AuthenticationPrincipal CustomUser user,
        ShareRequest request){
        service.shareFolderLink(user, request);
        log.info("request = {}", request);
        return "redirect:/link";
    }

    @PostMapping("/user")
    public String shareToUser(@AuthenticationPrincipal CustomUser user, UserShareRequest request) {
        service.shareFolderToUser(user, request);
        return "redirect:/link";
    }

    @GetMapping("/")
    public String getMySharedFolder(@AuthenticationPrincipal CustomUser user, Model model) {
        model.addAttribute("folders", service.getAllUserSharedFolder(user));
        return "sharedFolder";
    }
}
