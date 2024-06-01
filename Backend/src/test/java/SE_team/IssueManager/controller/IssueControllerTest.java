package SE_team.IssueManager.controller;

import SE_team.IssueManager.domain.Issue;
import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.enums.Category;
import SE_team.IssueManager.domain.enums.Priority;
import SE_team.IssueManager.domain.enums.Role;
import SE_team.IssueManager.domain.enums.Status;
import SE_team.IssueManager.dto.IssueRequestDto;
import SE_team.IssueManager.dto.MemberRequestDto;
import SE_team.IssueManager.service.IssueService;
import SE_team.IssueManager.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@NoArgsConstructor
@Transactional
@TestPropertySource(locations = "classpath:application-data.properties")
class IssueControllerTest {
    @Autowired
    private IssueService issueService;
    @Autowired
    private IssueController issueController;

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper=new ObjectMapper();

    private Member member;
    private Member dev;

    @Autowired
    private MemberService memberService;

    //이슈 생성 정보
    Long projectId=1L;
    String title1 ="title1";
    String description="description1";
    String priority="MINOR";
    Category category=Category.MEMORY_LEAK;


    Issue testIssue1;

    @BeforeEach
    void setUp() {
        //멤버 회원가입
        MemberRequestDto.SignUpRequestDTO memberDto = MemberRequestDto.SignUpRequestDTO.builder()
                .memberId("seoyeon2")
                .pw("1234")
                .role(Role.PL).build();
        member= memberService.signUp(memberDto);
        MemberRequestDto.SignUpRequestDTO memberDto2 = MemberRequestDto.SignUpRequestDTO.builder()
                .memberId("seoyeon4")
                .pw("1234")
                .role(Role.DEV).build();
        dev= memberService.signUp(memberDto2);

        Long reporterId=member.getId();

        //테스트 이슈 생성
        IssueRequestDto.CreateIssueRequestDto issueRequestDto1=IssueRequestDto.CreateIssueRequestDto.builder()
                .reporterId(reporterId)
                .title(title1)
                .description(description)
                .priority(Priority.valueOf(priority))
                .category(category).build();

        testIssue1=issueService.createIssue(projectId,issueRequestDto1);
    }

    @Test
    @DisplayName("이슈 생성")
    void createIssue() throws Exception {
        Long reporterId=member.getId();
        String title="title1";
        String description="description1";
        Priority priority=Priority.MINOR;
        Category category=Category.CRASH;

        //request body
        IssueRequestDto.CreateIssueRequestDto request=IssueRequestDto.CreateIssueRequestDto.builder()
                .reporterId(reporterId)
                .title(title)
                .description(description)
                .priority(priority)
                .category(category).build();

        String body=mapper.writeValueAsString(request);

        //이슈 post
        mockMvc.perform(MockMvcRequestBuilders.post("/issues/projects/{projectId}",1)
                .content(body)
                .contentType("application/json"))
                .andExpect(status().isOk());

        //생성한 이슈 reporterId로 조회(get)
        mockMvc.perform(MockMvcRequestBuilders.get("/issues/projects/{projectId}",1)
                        .param("reporterId",member.getMemberId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.issueList[0].title").value(title));  //반환된 issueList의 첫번째 issue와 title을 비교


    }
    @Test
    @DisplayName("이슈 수정")
    void issue_update() throws Exception {
        Long id=member.getId();
        IssueRequestDto.UpdateIssueStatusRequestDto request=IssueRequestDto.UpdateIssueStatusRequestDto.builder()
                .id(id)
                .status(Status.CLOSED)
                .assigneeId(null).build();
        String body=mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.patch("/issues/{issueId}",testIssue1.getId())
                        .content(body)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.issueId").value(testIssue1.getId()));
    }
    @Test
    @DisplayName("PL이 개발자에게 이슈를 assign")
    void assign_issue() throws Exception {
        Long id=member.getId();
        IssueRequestDto.UpdateIssueStatusRequestDto request=IssueRequestDto.UpdateIssueStatusRequestDto.builder()
                .id(id)
                .status(Status.ASSIGNED)
                .assigneeId(dev.getMemberId()).build();
        String body=mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.patch("/issues/{issueId}",testIssue1.getId())
                        .content(body)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.status").value(Status.ASSIGNED.toString()));
    }
    @Test
    @DisplayName("dev가 이슈를 fixed로 수정")
    void fix_issue_test()throws Exception{
        Long id=dev.getId();
        IssueRequestDto.UpdateIssueStatusRequestDto request=IssueRequestDto.UpdateIssueStatusRequestDto.builder()
                .id(id)
                .status(Status.FIXED)
                .assigneeId(null).build();
        String body=mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.patch("/issues/{issueId}",testIssue1.getId())
                        .content(body)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.status").value(Status.FIXED.toString()));
    }

    @Test
    @DisplayName("dev가 아닌 멤버가 이슈 fixed로 수정 시도")
    void issue_delete() throws Exception {
        Long id=member.getId();
        IssueRequestDto.UpdateIssueStatusRequestDto request=IssueRequestDto.UpdateIssueStatusRequestDto.builder()
                .id(id)
                .status(Status.FIXED)
                .assigneeId(null).build();
        String body=mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.patch("/issues/{issueId}",testIssue1.getId())
                        .content(body)
                        .contentType("application/json"))
                .andExpect(jsonPath("$.code").value("ISSUE_1001"));
    }
    @Test
    @DisplayName("이슈 삭제")
    void delete_issue() throws Exception {
        Long id=member.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/issues/{issueId}",testIssue1.getId())
                .param("memberId", String.valueOf(member.getId())))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("월별 이슈 통계")
    void monthly_issue_statistics() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/issues/projects/{projectId}/statistics",projectId)
                .param("month","5")
                .param("year","2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.issueCount").value("1"))
                .andExpect(jsonPath("$.result.issueCountByCategory[0]").value("1"));

    }


}