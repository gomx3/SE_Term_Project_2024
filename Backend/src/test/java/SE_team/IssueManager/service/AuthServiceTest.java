package SE_team.IssueManager.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.enums.Role;
import SE_team.IssueManager.dto.LoginRequestDto;
import SE_team.IssueManager.dto.LoginResponseDto.LoginRespDTO;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.ErrorStatus;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.payload.exception.LoginCheckFailException;
import SE_team.IssueManager.repository.MemberRepository;
import jakarta.transaction.Transactional;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-data.properties")
class AuthServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        // Create a test user in the database
        Member member = Member.builder()
                .memberId("user123")
                .pw(passwordEncoder.encode("password"))
                .role(Role.DEV)
                .build();
        memberRepository.save(member);
    }

    @Test
    @DisplayName("로그인 - 성공")
    void login_Success() throws Exception {
        // Given
        LoginRequestDto loginRequest = new LoginRequestDto("user123", "password");
        ApiResponse<LoginRespDTO> response = ApiResponse.onSuccess(SuccessStatus.MEMBER_OK, new LoginRespDTO());
        when(authService.login(any(LoginRequestDto.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 - 실패 (아이디 없음)")
    void login_Fail_NoUsername() throws Exception {
        // Given
        LoginRequestDto loginRequest = new LoginRequestDto("", "password");
        when(authService.login(any(LoginRequestDto.class)))
                .thenThrow(new LoginCheckFailException(ErrorStatus.INVALID_USERNAME));

        // When & Then
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인 - 실패 (비밀번호 오류)")
    void login_Fail_WrongPassword() throws Exception {
        // Given
        LoginRequestDto loginRequest = new LoginRequestDto("user123", "wrongpassword");
        when(authService.login(any(LoginRequestDto.class)))
                .thenThrow(new LoginCheckFailException(ErrorStatus.INVALID_PASSWORD));

        // When & Then
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인 - 실패 (인증 실패)")
    void login_Fail_AuthenticationError() throws Exception {
        // Given
        LoginRequestDto loginRequest = new LoginRequestDto("user123", "password");
        when(authService.login(any(LoginRequestDto.class)))
                .thenThrow(new LoginCheckFailException(ErrorStatus.INVALID_CREDENTIALS));

        // When & Then
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그아웃 - 성공")
    void logout_Success() throws Exception {

        // When & Then
        mockMvc.perform(post("/logout")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}