package SE_team.IssueManager.controller;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.enums.Role;
import SE_team.IssueManager.dto.MemberRequestDto;
import SE_team.IssueManager.repository.MemberRepository;
import SE_team.IssueManager.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureMockMvc
@SpringBootTest
@NoArgsConstructor
@Transactional
class MemberControllerTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();


    @Test
    void 회원가입() throws Exception{

        String pw="1234";
        String memberId= "spring";
        Role role=Role.ADMIN;

        MemberRequestDto.SignUpRequestDTO request=MemberRequestDto.SignUpRequestDTO.builder()
                .pw(pw)
                .memberId(memberId)
                .role(role).build();


        String body=mapper.writeValueAsString(request);

        ResultActions action=mockMvc.perform(MockMvcRequestBuilders.post("/member/sign_up")
                .content(body)
                .contentType("application/json"));

        action.andExpect(result -> {
            MockHttpServletResponse response=result.getResponse();
            System.out.println(response.getContentAsString());
        });


        Member findMem=memberService.findMemberById(1L).get();
        assertEquals(findMem.getMemberId(),memberId);

    }
    @Test
    void 중복이메일()throws Exception{   //아직 미완성 (예외처리 작업중)..

        String pw="1234";
        String memberId="spring";
        Role role=Role.ADMIN;

        MemberRequestDto.SignUpRequestDTO request1=MemberRequestDto.SignUpRequestDTO.builder()
                .pw(pw)
                .memberId(memberId)
                .role(role).build();
        MemberRequestDto.SignUpRequestDTO request2=MemberRequestDto.SignUpRequestDTO.builder()
                .pw(pw)
                .memberId(memberId)
                .role(role).build();


        String body1=mapper.writeValueAsString(request1);
        String body2=mapper.writeValueAsString(request2);

        ResultActions action=mockMvc.perform(MockMvcRequestBuilders.post("/members/sign-up")
                .content(body1)
                .contentType("application/json"));


        action.andExpect(result -> {
            MockHttpServletResponse response=result.getResponse();
            System.out.println(response.getContentAsString());
        });

        ServletException e=assertThrows(ServletException.class,
                ()->mockMvc.perform(MockMvcRequestBuilders.post("/members/sign-up")
                        .content(body2)
                        .contentType("application/json")));
        //assertEquals(e.getMessage(),"이미 존재하는 회원입니다.");

    }
}