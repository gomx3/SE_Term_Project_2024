package SE_team.IssueManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginViewController {
    @GetMapping("/account/login")
    public String login(@RequestParam(required = false) boolean hasMessage,
            @RequestParam(required = false) String message,
            Model model) {

        model.addAttribute("hasMessage", hasMessage);
        model.addAttribute("message", message);
        return "login/login";
    }
}