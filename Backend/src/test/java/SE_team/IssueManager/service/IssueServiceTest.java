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
import SE_team.IssueManager.repository.IssueRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Month;
import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class IssueServiceTest {
    @Autowired
    IssueService issueService;

    @Autowired
    MemberService memberService;
    @Autowired
    IssueRepository issueRepository;
    Member member;

    @BeforeEach
    void setUp() {
        MemberRequestDto.SignUpRequestDTO memberDto = MemberRequestDto.SignUpRequestDTO.builder()
                .memberId("spring")
                .pw("1234")
                .role(Role.ADMIN).build();
        member= memberService.signUp(memberDto);
    }
//    @AfterEach
//    void cleanUp(){
//        issueRepository.deleteAll();
//    }


    @Test
    void createIssue() {
        //Given
        Long reporterId=member.getId();
        Long projectId=1L;

        String title="title1";
        String title2="title2";
        String description="description1";
        String priority="MINOR";
        String priority2="MAJOR";
        Category category=Category.MEMORY_LEAK;

        IssueRequestDto.CreateIssueRequestDto issueRequestDto1=IssueRequestDto.CreateIssueRequestDto.builder()
                .reporterId(reporterId)
                .title(title)
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

        //When
        Issue savedIssue1=issueService.createIssue(projectId,issueRequestDto1);
        Issue savedIssue2=issueService.createIssue(projectId,issueRequestDto2);


        //Then
        //reporterId로 이슈 불러오기
        List<Issue> issueList;

        //조건으로 찾기
        //0) 전체 조회
        issueList=issueService.findByCondition(null,null,null,null,null,null);
        for(Issue issue:issueList){
            System.out.println(issue.getTitle()+":"+issue.getCreatedAt()+"프로젝트:"+issue.getProjectId());
        }
        //1) reporter id로 찾기
        issueList=issueService.findByCondition("spring",null,null,null,null,null);
        System.out.println("reporterId로 찾기:");
        for(Issue issue:issueList){
            System.out.println(issue.getTitle()+":"+issue.getCreatedAt()+issue.getPriority()+issue.getCategory());
        }
        //2) fixerId로 찾기
        issueList=issueService.findByCondition(null,"spring",null,null,null,null);
        System.out.println("fixerId로 찾기:");
        for(Issue issue:issueList){
            System.out.println(issue.getTitle()+":"+issue.getCreatedAt());
        }
        //3) priority 로 찾기
        issueList=issueService.findByCondition(null,null,null,null,Priority.MAJOR,null);
        System.out.println("우선순위로 찾기:");
        for(Issue issue:issueList){
            System.out.println(issue.getTitle()+":"+issue.getCreatedAt());
        }
        //통계
        IssueResponseDto.GetStatisticsResponseDto issueStatistics=issueService.getIssueStatistics(2024,5,1L);
        List<Issue> issueList2=issueRepository.findByProjectIdAndYearAndMonth(1L,2024,5);
        System.out.println("2024-5 이슈");
        for(Issue issue:issueList2){
            System.out.println(issue.getTitle()+":"+issue.getCreatedAt());
        }
        System.out.println("이슈 개수:"+issueStatistics.getIssueCount());

        //개발자 추천
        long[] devIdList= issueRepository.findDevByCategory(1L,"MEMORY_LEAK");
        System.out.println("Memory leak 카테고리: ");
        for(long devId:devIdList){
            System.out.println(devId+" ");
        }
    }
}