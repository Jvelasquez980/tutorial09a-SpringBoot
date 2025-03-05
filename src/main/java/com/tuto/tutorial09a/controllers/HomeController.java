package com.tuto.tutorial09a.controllers;


import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class HomeController {


@GetMapping("/")

public String index(Model model) {

model.addAttribute("title", "Welcome to Spring Boot");

model.addAttribute("subtitle", "An Spring Boot Eafit App");

return "home/index";

}


@GetMapping("/about")

public String about(Model model) {

model.addAttribute("title", "About Us - Online Store");

model.addAttribute("subtitle", "About Us");

model.addAttribute("description", "This is an about page ...");

model.addAttribute("author", "Developed by: Your Name");

return "home/about";

}

}