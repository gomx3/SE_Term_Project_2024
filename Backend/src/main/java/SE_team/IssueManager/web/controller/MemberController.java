package SE_team.IssueManager.web.controller;

import SE_team.IssueManager.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.web.dto.MemberRequestDto;
import SE_team.IssueManager.web.dto.MemberResponseDto;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.service.MemberService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Autowired
    MemberController(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
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

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody MemberResponseDto.MemberSaveDTO memberSaveDto) {
        memberService.save(memberSaveDto);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMember(@RequestParam(name="id") Long id) {
        memberRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
