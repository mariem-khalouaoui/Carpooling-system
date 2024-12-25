package com.tunisiologia.TunisiologiaApp.controllers;

import com.tunisiologia.TunisiologiaApp.Entities.Numbers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String showHomePage(Model model) {
        model.addAttribute("numbers", new Numbers());
        return "home";
    }
    @PostMapping("/addNumbers")
    public String addNumbers(@ModelAttribute Numbers numbers, Model model) {
        int result = numbers.getNum1() + numbers.getNum2();

        model.addAttribute("result", result);
        return "result";
    }
}
