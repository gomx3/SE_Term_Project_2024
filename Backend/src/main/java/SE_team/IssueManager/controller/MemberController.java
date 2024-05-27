package SE_team.IssueManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.dto.MemberRequestDto;
import SE_team.IssueManager.dto.MemberResponseDto;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.service.MemberService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/sign-up")
    public ApiResponse<MemberResponseDto.SingUpRespDTO> signUp(
            @Valid @RequestBody MemberRequestDto.SignUpRequestDTO signUpRequestDto) {
        Member member = memberService.signUp(signUpRequestDto);
        MemberResponseDto.SingUpRespDTO resp = MemberResponseDto.SingUpRespDTO.builder()
                .id(member.getId()).build();
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_OK, resp);
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
}
