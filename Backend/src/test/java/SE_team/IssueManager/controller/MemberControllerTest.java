package SE_team.IssueManager.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import SE_team.IssueManager.domain.enums.Role;
import SE_team.IssueManager.web.dto.MemberRequestDto;
import SE_team.IssueManager.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;

@AutoConfigureMockMvc
@SpringBootTest
@NoArgsConstructor
@Transactional
@TestPropertySource(locations = "classpath:application-data.properties")
class MemberControllerTest {
        @Autowired
        private MemberService memberService;

        @Autowired
        private MockMvc mockMvc;
        private ObjectMapper mapper = new ObjectMapper();

        @Test
        @DisplayName("회원가입")
        void sign_up() throws Exception {

                String pw = "1234";
                String memberId = "seoyeon2";
                Role role = Role.ADMIN;

                MemberRequestDto.SignUpRequestDTO request = MemberRequestDto.SignUpRequestDTO.builder()
                                .pw(pw)
                                .memberId(memberId)
                                .role(role).build();
                String body = mapper.writeValueAsString(request);

                mockMvc.perform(MockMvcRequestBuilders.post("/members/sign-up")
                                .content(body)
                                .contentType("application/json"))
                                .andExpect(status().isOk());

        }

        @Test
        @DisplayName("중복된 memberId 설정")
        void sign_up_duplicated_memberId() throws Exception { // 아직 미완성 (예외처리 작업중)..

                String pw = "1234";
                String memberId = "spring";
                Role role = Role.ADMIN;

                MemberRequestDto.SignUpRequestDTO request1 = MemberRequestDto.SignUpRequestDTO.builder()
                                .pw(pw)
                                .memberId(memberId)
                                .role(role).build();

                // memberId가 같은 회원가입 요청
                MemberRequestDto.SignUpRequestDTO request2 = MemberRequestDto.SignUpRequestDTO.builder()
                                .pw(pw)
                                .memberId(memberId)
                                .role(role).build();

                String body1 = mapper.writeValueAsString(request1);
                String body2 = mapper.writeValueAsString(request2);

                mockMvc.perform(MockMvcRequestBuilders.post("/members/sign-up")
                                .content(body1)
                                .contentType("application/json"))
                                .andExpect(status().isOk());

                mockMvc.perform(MockMvcRequestBuilders.post("/members/sign-up")
                                .content(body2)
                                .contentType("application/json"))
                                .andExpect(jsonPath("$.code").value("MEMBER_1000"));

        }
}