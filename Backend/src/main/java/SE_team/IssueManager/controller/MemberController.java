package SE_team.IssueManager.controller;

import SE_team.IssueManager.ApiResponse;
import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.dto.MemberRequestDto;
import SE_team.IssueManager.dto.MemberResponseDto;
import SE_team.IssueManager.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.codec.ServerSentEvent.builder;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    MemberController(MemberService memberService) {
        this.memberService=memberService;
    }

    @PostMapping("/sign_up")
    public ApiResponse<MemberResponseDto.SingUpRespDTO> signUp(
            @Valid @RequestBody MemberRequestDto.SignUpRequestDTO signUpRequestDto
    ){
        Member member=memberService.signUp(signUpRequestDto);
        MemberResponseDto.SingUpRespDTO resp=MemberResponseDto.SingUpRespDTO.builder()
                .memberId(member.getId()).build();
        return ApiResponse.created(resp);
    }

}
