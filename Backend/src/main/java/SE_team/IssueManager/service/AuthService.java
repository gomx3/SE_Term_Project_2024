package SE_team.IssueManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import SE_team.IssueManager.dto.LoginRequestDto;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.ErrorStatus;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.payload.exception.LoginCheckFailException;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    public ApiResponse<?> login(LoginRequestDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getMemberId(), loginRequest.getPw()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ApiResponse.onSuccess(SuccessStatus.LOGIN_SUCCESS, "Login successful");
        } catch (AuthenticationException e) {
            throw new LoginCheckFailException(ErrorStatus.INVALID_CREDENTIALS);
        }
    }

    public ApiResponse<?> logout() {
        SecurityContextHolder.clearContext();
        return ApiResponse.onSuccess(SuccessStatus.LOGOUT_SUCCESS, "Logout successful");
    }
}
