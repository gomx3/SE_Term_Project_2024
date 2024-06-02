package SE_team.IssueManager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SE_team.IssueManager.web.dto.LoginRequestDto;
import SE_team.IssueManager.web.dto.LoginResponseDto.LoginRespDTO;
import SE_team.IssueManager.payload.ApiResponse;
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
    public ApiResponse<LoginRespDTO> login(@RequestBody LoginRequestDto loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/logout")
    public ApiResponse<?> logoutUser() {
        return authService.logout();
    }

}
