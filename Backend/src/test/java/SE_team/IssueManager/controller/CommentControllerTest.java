package SE_team.IssueManager.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import SE_team.IssueManager.domain.Comment;
import SE_team.IssueManager.domain.Issue;
import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.enums.Category;
import SE_team.IssueManager.domain.enums.Priority;
import SE_team.IssueManager.domain.enums.Role;
import SE_team.IssueManager.dto.CommentRequestDto;
import SE_team.IssueManager.dto.IssueRequestDto;
import SE_team.IssueManager.dto.MemberRequestDto;
import SE_team.IssueManager.service.CommentService;
import SE_team.IssueManager.service.IssueService;
import SE_team.IssueManager.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;

@AutoConfigureMockMvc
@SpringBootTest
@NoArgsConstructor
@Transactional
@TestPropertySource(locations = "classpath:application-data.properties")
class CommentControllerTest {

        @Autowired
        private CommentController commentController;
        @Autowired
        private MemberService memberService;
        @Autowired
        private IssueService issueService;
        @Autowired
        private CommentService commentService;

        @Autowired
        private MockMvc mockMvc;
        private ObjectMapper mapper = new ObjectMapper();

        Member member;
        Issue testIssue1;
        Comment testComment;

        // 이슈 생성 정보
        Long projectId = 1L;
        String title1 = "title1";
        String description = "description1";
        String priority = "MINOR";
        Category category = Category.MEMORY_LEAK;

        // 코멘트 생성 정보
        String content1 = "content1";
        String content2 = "content2";

        @BeforeEach
        void setUp() {
                // 멤버 회원가입
                MemberRequestDto.SignUpRequestDTO memberDto = MemberRequestDto.SignUpRequestDTO.builder()
                                .memberId("seoyeon2")
                                .pw("1234")
                                .role(Role.PL).build();
                member = memberService.signUp(memberDto);

                Long reporterId = member.getId();

                // 테스트 이슈 생성
                IssueRequestDto.CreateIssueRequestDto issueRequestDto1 = IssueRequestDto.CreateIssueRequestDto.builder()
                                .reporterId(reporterId)
                                .title(title1)
                                .description(description)
                                .priority(Priority.valueOf(priority))
                                .category(category).build();

                testIssue1 = issueService.createIssue(projectId, issueRequestDto1);

                // 테스트 코멘트 생성
                CommentRequestDto.CreateCommentDto request = CommentRequestDto.CreateCommentDto.builder()
                                .id(member.getId())
                                .content(content1).build();

                testComment = commentService.createComment(request, testIssue1.getId());
        }

        @Test
        @DisplayName("코멘트 생성")
        void create_comment() throws Exception {
                CommentRequestDto.CreateCommentDto request = CommentRequestDto.CreateCommentDto.builder()
                                .id(member.getId())
                                .content(content2).build();
                String body = mapper.writeValueAsString(request);
                mockMvc.perform(MockMvcRequestBuilders.post("/comments/issues/{issueId}", testIssue1.getId())
                                .content(body)
                                .contentType("application/json"))
                                .andExpect(status().isOk());
        }

        @Test
        @DisplayName("특정 이슈의 코멘트 리스트 불러오기")
        void get_comment() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/comments/issues/{issueId}", testIssue1.getId()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.result.commentList[0].commentId").value(testComment.getId()));
        }

}