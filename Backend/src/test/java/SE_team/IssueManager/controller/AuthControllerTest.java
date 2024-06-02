package SE_team.IssueManager.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import SE_team.IssueManager.dto.LoginRequestDto;
import SE_team.IssueManager.dto.LoginResponseDto.LoginRespDTO;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.service.AuthService;
import jakarta.transaction.Transactional;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-data.properties")
class AuthControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private AuthService authService;

        @MockBean
        private AuthenticationManager authenticationManager;

        @Autowired
        private ObjectMapper mapper;

        @Test
        @DisplayName("로그인 테스트")
        void login() throws Exception {
                // Given
                LoginRequestDto loginRequest = new LoginRequestDto("seoyeon", "1234");
                LoginRespDTO loginRespDTO = new LoginRespDTO(1L, "seoyeon", "ADMIN");

                ApiResponse<LoginRespDTO> response = ApiResponse.onSuccess(SuccessStatus.LOGIN_SUCCESS, loginRespDTO);

                when(authService.login(any(LoginRequestDto.class))).thenReturn(response);

                // When & Then
                mockMvc.perform(post("/members/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(loginRequest)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.result.id").value(1L))
                                .andExpect(jsonPath("$.result.memberId").value("seoyeon"))
                                .andExpect(jsonPath("$.result.role").value("ADMIN"));
        }

        @Test
        @DisplayName("로그아웃 테스트")
        void logout() throws Exception {
                // When & Then
                mockMvc.perform(post("/members/logout")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk());
        }

}
