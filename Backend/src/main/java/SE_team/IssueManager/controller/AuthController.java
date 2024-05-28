package SE_team.IssueManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SE_team.IssueManager.dto.LoginRequestDto;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.ErrorStatus;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.service.AuthService;

@RestController
@RequestMapping("/members")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getMemberId(), loginRequest.getPw()));
            // 성공 처리 로직 (예: 세션 설정, JWT 발급 등)
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ApiResponse.onSuccess(SuccessStatus.LOGIN_SUCCESS, "Login successful");
        } catch (AuthenticationException e) {
            return ApiResponse.onFailure(ErrorStatus.INVALID_CREDENTIALS.getCode(), "Invalid username or password",
                    null);
        }
    }

    @PostMapping("/logout")
    public ApiResponse<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ApiResponse.onSuccess(SuccessStatus.LOGOUT_SUCCESS, "Logout successful");
    }
}
