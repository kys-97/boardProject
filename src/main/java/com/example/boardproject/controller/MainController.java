package com.example.boardproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String root() {

        return "redirect:/post/list";       //--> 매핑될 URL "페이지이름 아님 주의"
    }
    @GetMapping("/post/listpage")
    public String listpageing() {

        return "redirect:/post/list";       //--> 매핑될 URL "페이지이름 아님 주의"
    }

}
