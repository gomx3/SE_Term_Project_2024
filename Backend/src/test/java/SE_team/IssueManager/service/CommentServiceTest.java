package SE_team.IssueManager.service;

import SE_team.IssueManager.domain.Project;
import SE_team.IssueManager.repository.ProjectRepository;
import SE_team.IssueManager.web.controller.CommentController;
import SE_team.IssueManager.domain.Comment;
import SE_team.IssueManager.domain.Issue;
import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.enums.Category;
import SE_team.IssueManager.domain.enums.Priority;
import SE_team.IssueManager.domain.enums.Role;
import SE_team.IssueManager.web.dto.CommentRequestDto;
import SE_team.IssueManager.web.dto.IssueRequestDto;
import SE_team.IssueManager.web.dto.MemberRequestDto;
import SE_team.IssueManager.repository.CommentRepository;
import SE_team.IssueManager.web.dto.ProjectRequestDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-data.properties")
class CommentServiceTest {
    @Autowired
    private CommentController commentController;
    @Autowired
    private MemberService memberService;
    @Autowired
    private IssueService issueService;
    @Autowired
    private ProjectService projectService;

    Member member;
    Issue testIssue1;
    Comment savedComment;

    //이슈 생성 정보
    Long projectId=1L;
    String title1 ="title1";
    String description="description1";
    String priority="MINOR";
    Category category=Category.MEMORY_LEAK;

    //코멘트 생성 정보
    String content1 ="content1";
    String content2 ="content2";


    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ProjectMemberService projectMemberService;

    @BeforeEach
    void setUp() {
        //멤버 회원가입
        MemberRequestDto.SignUpRequestDTO memberDto = MemberRequestDto.SignUpRequestDTO.builder()
                .memberId("seoyeon2")
                .pw("1234")
                .role(Role.PL).build();
        member= memberService.signUp(memberDto);


        Long reporterId=member.getId();

        //테스트 프로젝트 생성
        ProjectRequestDto.CreateProjectRequestDTO createProjectRequestDTO=ProjectRequestDto.CreateProjectRequestDTO.builder()
                .creatorId("seoyeon2")
                .name("project1").build();
        projectId=projectService.createProject(createProjectRequestDTO).getResult().getId();

        //프로젝트에 멤버 추가
        projectMemberService.addMemberToProject(projectId,"seoyeon2");

        //테스트 이슈 생성
        IssueRequestDto.CreateIssueRequestDto issueRequestDto1=IssueRequestDto.CreateIssueRequestDto.builder()
                .reporterId(reporterId)
                .title(title1)
                .description(description)
                .priority(Priority.valueOf(priority))
                .category(category).build();

        testIssue1=issueService.createIssue(projectId,issueRequestDto1);

        //테스트 코멘트 생성
        CommentRequestDto.CreateCommentDto request=CommentRequestDto.CreateCommentDto.builder()
                .id(member.getId())
                .content(content1).build();

        savedComment=commentService.createComment(request, testIssue1.getId());
    }

    @Test
    @DisplayName("코멘트 생성")
    void create_comment(){
        CommentRequestDto.CreateCommentDto request=CommentRequestDto.CreateCommentDto.builder()
                .id(member.getId())
                .content(content2).build();

        Comment savedComment=commentService.createComment(request, testIssue1.getId());
        assertEquals(content2,savedComment.getContent());
    }

    @Test
    @DisplayName("특정 이슈의 코멘트 조회")
    void get_comment(){
        List<Comment> commentList=commentService.getComments(testIssue1.getId());
        assertEquals(content1,commentList.get(0).getContent());
    }

}