package SE_team.IssueManager.service;

import SE_team.IssueManager.domain.Issue;
import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.enums.Category;
import SE_team.IssueManager.domain.enums.Priority;
import SE_team.IssueManager.domain.enums.Role;
import SE_team.IssueManager.domain.enums.Status;
import SE_team.IssueManager.dto.IssueRequestDto;
import SE_team.IssueManager.dto.IssueResponseDto;
import SE_team.IssueManager.dto.MemberRequestDto;
import SE_team.IssueManager.payload.exception.handler.IssueHandler;
import SE_team.IssueManager.repository.IssueRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-data.properties")
class IssueServiceTest {

    @Autowired
    IssueService issueService;
    @Autowired
    MemberService memberService;
    @Autowired
    IssueRepository issueRepository;

    Member member;
    Member dev;

    //이슈 생성 정보
    Long projectId=1L;

    String title1 ="title1";
    String title2="title2";

    String description="description1";

    String priority="MINOR";
    String priority2="MAJOR";

    Category category=Category.MEMORY_LEAK;

    //이슈 조회 리스트
    List<Issue> issueList;

    Issue testIssue1;
    Issue testIssue2;


    @BeforeEach
    void setUp() {
        //멤버 회원가입
        //PL 멤버
        MemberRequestDto.SignUpRequestDTO memberDto = MemberRequestDto.SignUpRequestDTO.builder()
                .memberId("name1")
                .pw("1234")
                .role(Role.PL).build();
        member= memberService.signUp(memberDto);

        //dev 멤버
        MemberRequestDto.SignUpRequestDTO memberDto2 = MemberRequestDto.SignUpRequestDTO.builder()
                .memberId("name2")
                .pw("1234")
                .role(Role.DEV).build();
        dev=memberService.signUp(memberDto2);

        //이슈 생성
        Long reporterId=member.getId();

        IssueRequestDto.CreateIssueRequestDto issueRequestDto1=IssueRequestDto.CreateIssueRequestDto.builder()
                .reporterId(reporterId)
                .title(title1)
                .description(description)
                .priority(Priority.valueOf(priority))
                .category(category).build();

        IssueRequestDto.CreateIssueRequestDto issueRequestDto2=IssueRequestDto.CreateIssueRequestDto.builder()
                .reporterId(reporterId)
                .title(title2)
                .description(description)
                .priority(Priority.valueOf(priority2))
                .category(category)
                .build();

        testIssue1=issueService.createIssue(projectId,issueRequestDto1);
        testIssue2=issueService.createIssue(projectId,issueRequestDto2);
    }


    @Test
    void createIssue() {
        //Given
        Long reporterId=member.getId();
        //이슈1
        IssueRequestDto.CreateIssueRequestDto issueRequestDto1=IssueRequestDto.CreateIssueRequestDto.builder()
                .reporterId(reporterId)
                .title(title1)
                .description(description)
                .priority(Priority.valueOf(priority))
                .category(category).build();
        //이슈2
        IssueRequestDto.CreateIssueRequestDto issueRequestDto2=IssueRequestDto.CreateIssueRequestDto.builder()
                .reporterId(reporterId)
                .title(title2)
                .description(description)
                .priority(Priority.valueOf(priority2))
                .category(category)
                .build();

        //When
        Issue savedIssue1=issueService.createIssue(projectId,issueRequestDto1);
        Issue savedIssue2=issueService.createIssue(projectId,issueRequestDto2);

        assertEquals(title1,savedIssue1.getTitle());
        assertEquals(title2,savedIssue2.getTitle());
    }


    @Test
    @DisplayName("reporter id로 이슈 조회")
    void find_by_reporter_id(){
        //findByCondition 메소드는 조건에 맞는 이슈를 생성시점을 기준으로 내림차순으로 정렬하여 반환한다.
        issueList=issueService.findByCondition("name1",null,null,null,null,null);
        assertEquals(2,issueList.size());
        assertEquals(testIssue2.getTitle(),issueList.get(0).getTitle());
        assertEquals(testIssue1.getTitle(),issueList.get(1).getTitle());
    }

    @Test
    @DisplayName("priority 로 이슈 조회")
    void find_by_priority(){
        issueList=issueService.findByCondition(null,null,null,null,Priority.MAJOR,null);
        assertEquals(1, issueList.size());
    }

    @Test
    @DisplayName("PL이 이슈에 fixer를 assign")
    void assign_fixer_test(){
        Long plId=member.getId();
        Issue updatedIssue=issueService.updateIssueState(plId,testIssue1.getId(),Status.ASSIGNED, dev.getMemberId());
        assertEquals(Status.ASSIGNED,updatedIssue.getStatus());
    }


    @Nested
    @DisplayName("이슈 상태 fix 수정 테스트")
    class fix_test{
        @Test
        @DisplayName("이슈 상태 fixed 로 변경")
        void fix_issue(){
            Long devId=dev.getId();
            Issue updatedIssue=issueService.updateIssueState(devId,testIssue1.getId(),Status.FIXED,"");
            assertEquals(Status.FIXED,updatedIssue.getStatus());

        }
        @Test
        @DisplayName("dev가 아닌 멤버가 이슈 fixed 상태로 변경 시도")
        void fix_issue_not_dev(){
            Long memberId=member.getId();
            assertThrows(IssueHandler.class,()->issueService.updateIssueState(memberId,testIssue1.getId(),Status.FIXED,""));
        }
        @Test
        @DisplayName("fixerId로 이슈 조회")
        void find_issue_by_fixer_id(){
            fix_issue();
            issueList=issueService.findByCondition(null,dev.getMemberId(),null,null,null,null);
            assertEquals(1,issueList.size());
            assertEquals(testIssue1.getTitle(),issueList.get(0).getTitle());
        }
        @Test
        @DisplayName("특정 category 에 대해 개발자 추천")
        void recommend_dev(){
            fix_issue();
            ArrayList<String> devList=issueService.getDevRecommend(projectId,category);
            assertEquals(dev.getMemberId(),devList.get(0));
        }
    }

    @Test
    @DisplayName("월별 이슈 통계")
    void monthly_issue_statistics(){
        IssueResponseDto.GetStatisticsResponseDto statistics=issueService.getIssueStatistics(2024,5,projectId);
        assertEquals(2,statistics.getIssueCount());
        assertEquals(2,statistics.getIssueCountByCategory()[0]);
    }



}