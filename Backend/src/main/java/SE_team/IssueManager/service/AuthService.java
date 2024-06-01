package SE_team.IssueManager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.dto.LoginRequestDto;
import SE_team.IssueManager.dto.LoginResponseDto.LoginRespDTO;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.ErrorStatus;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.payload.exception.LoginCheckFailException;
import SE_team.IssueManager.repository.MemberRepository;
import SE_team.IssueManager.security.CustomUserDetails;
import SE_team.IssueManager.security.CustomUserDetailsService;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordService passwordService;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder; // BCryptPasswordEncoder 추가
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, PasswordService passwordService,
            MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder,
            CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.passwordService = passwordService;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder; // passwordEncoder 초기화
        this.userDetailsService = userDetailsService;

    }

    public ApiResponse<LoginRespDTO> login(LoginRequestDto loginRequest) {
        try {
            // 사용자의 ID를 통해 데이터베이스에서 사용자 정보 조회
            Optional<Member> optionalMember = memberRepository.findByMemberId(loginRequest.getMemberId());
            if (!optionalMember.isPresent()) {
                // 사용자가 존재하지 않으면 인증 실패 처리
                throw new LoginCheckFailException(ErrorStatus.INVALID_USERNAME);
            }

            // 사용자가 존재하고 비밀번호도 일치하면 인증 성공 처리
            String encryptedPassword = optionalMember.get().getPw(); // 암호화된 비밀번호 가져오기
            if (!passwordEncoder.matches(loginRequest.getPw(), encryptedPassword)) {
                throw new LoginCheckFailException(ErrorStatus.INVALID_PASSWORD);
            }

            // 사용자가 존재하고 비밀번호도 일치하면 인증 성공 처리
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getMemberId(),
                    loginRequest.getPw());

            /*
             * authenticationManager.authenticate(authentication);
             * SecurityContextHolder.getContext().setAuthentication(authentication);
             * return ApiResponse.onSuccess(SuccessStatus.LOGIN_SUCCESS,
             * "Login successful");
             */

            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService
                    .loadUserByUsername(loginRequest.getMemberId());

            LoginRespDTO response = new LoginRespDTO();
            response.setId(userDetails.getMember().getId());
            response.setMemberId(userDetails.getMember().getMemberId());
            response.setRole(userDetails.getMember().getRole().name());

            return ApiResponse.onSuccess(SuccessStatus.MEMBER_OK, response);
        } catch (AuthenticationException e) {
            // 인증 실패 시에는 인증 실패 처리
            throw new LoginCheckFailException(ErrorStatus.INVALID_CREDENTIALS);
        }
    }

    public ApiResponse<?> logout() {
        SecurityContextHolder.clearContext();
        return ApiResponse.onSuccess(SuccessStatus.LOGOUT_SUCCESS, "Logout successful");
    }
}
