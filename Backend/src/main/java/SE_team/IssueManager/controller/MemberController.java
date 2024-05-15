package SE_team.IssueManager.controller;

import SE_team.IssueManager.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    MemberController(MemberService memberService) {
        this.memberService=memberService;
    }


}
