package com.example.security.controller;

import com.example.security.login.AccountDTO;
import com.example.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    public String postRegister(AccountDTO user,
                               RedirectAttributes redirectAttributes) {
        try{
            loginService.register(user);
            redirectAttributes.addFlashAttribute("msg", "정상적으로 등록되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", e.getMessage()); // throw new Exception("중복 유저")
            return "redirect:/register";
        }
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        // TODO : 로그인 여부를 감지해서 들어오지 못하게 설정
        model.addAttribute("user", new AccountDTO());
        return "register";
    }

    @GetMapping // index
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
