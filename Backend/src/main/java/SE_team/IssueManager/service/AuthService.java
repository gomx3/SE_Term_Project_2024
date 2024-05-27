package SE_team.IssueManager.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.dto.LoginRequestDto;
import SE_team.IssueManager.dto.MemberRequestDto.SignUpRequestDTO;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.ErrorStatus;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.payload.exception.handler.MemberHandler;
import SE_team.IssueManager.repository.MemberRepository;

@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ApiResponse<?> signup(SignUpRequestDTO request) {
        Member member = Member.builder()
                .memberId(request.getMemberId())
                .pw(passwordEncoder.encode(request.getPw()))
                .role(request.getRole())
                .build();
        memberRepository.save(member);
        return ApiResponse.onSuccess(SuccessStatus.SIGNUP_SUCCESS, null);
    }

    public ApiResponse<?> login(LoginRequestDto request) {
        Member member = memberRepository.findByMemberId(request.getMemberId())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        if (!passwordEncoder.matches(request.getPw(), member.getPw())) {
            throw new MemberHandler(ErrorStatus.INVALID_PASSWORD);
        }
        return ApiResponse.onSuccess(SuccessStatus.LOGIN_SUCCESS, null);
    }
}
