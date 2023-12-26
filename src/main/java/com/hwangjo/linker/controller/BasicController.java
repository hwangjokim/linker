package com.hwangjo.linker.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/")
public class BasicController {

    @GetMapping
    public String mainPage(Principal principal){
        if (principal == null)
            return "anonymous_main";
        return "redirect:/link";
    }
}
