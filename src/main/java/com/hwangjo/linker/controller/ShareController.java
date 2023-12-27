package com.hwangjo.linker.controller;


import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.dto.SharedFolderResponse;
import com.hwangjo.linker.service.FolderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/share")
public class ShareController {
    private final FolderService service;

    @GetMapping("/{folderId}")
    public String getShareFolder(@PathVariable("folderId") UUID folderId, Model model) {
        SharedFolderResponse folder = service.getSharedFolder(folderId);
        model.addAttribute("folder", folder);
        return "share";
    }
}
