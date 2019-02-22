package com.bookstore.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebMvcController {

	@GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login";
    }
}