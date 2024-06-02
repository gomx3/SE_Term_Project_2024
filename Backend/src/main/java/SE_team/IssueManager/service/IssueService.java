package SE_team.IssueManager.service;

import SE_team.IssueManager.domain.Issue;
import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.enums.Category;
import SE_team.IssueManager.domain.enums.Priority;
import SE_team.IssueManager.domain.enums.Status;
import SE_team.IssueManager.web.dto.IssueRequestDto;
import SE_team.IssueManager.web.dto.IssueResponseDto;
import SE_team.IssueManager.payload.code.status.ErrorStatus;
import SE_team.IssueManager.payload.exception.handler.IssueHandler;
import SE_team.IssueManager.payload.exception.handler.MemberHandler;
import SE_team.IssueManager.domain.Project;
import SE_team.IssueManager.repository.ProjectRepository;
import SE_team.IssueManager.repository.IssueRepository;
import SE_team.IssueManager.repository.MemberRepository;
import SE_team.IssueManager.domain.specification.IssueSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static SE_team.IssueManager.domain.enums.Role.DEV;
import static SE_team.IssueManager.domain.enums.Role.PL;

@Service
@RequiredArgsConstructor
@Transactional
public class IssueService {

    @Autowired
    private final IssueRepository issueRepository;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private ProjectRepository projectRepository;

    //이슈 생성
    public Issue createIssue(Long projectId,IssueRequestDto.CreateIssueRequestDto request) {
        Member reporter=memberRepository.findById(request.getReporterId()).orElse(null);
        if(reporter==null) throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        Project project=projectRepository.findById(projectId).orElse(null);
        if(project==null) throw new IssueHandler(ErrorStatus.ISSUE_PROJECT_NOT_FOUND);

        Issue issue=Issue.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .reporter(reporter)
                .project(project)
                .priority(request.getPriority())
                .category(request.getCategory())
                .build();

        Issue savedIssue= issueRepository.save(issue);
        return savedIssue;
    }


    public List<Issue> findByCondition(Long projectId,String reporterId, String fixerId, String assigneeId, Status status, Priority priority, Category category ){
        Sort sorting=Sort.by(Sort.Order.desc("createdAt"));
        Specification<Issue> spec = (root,query,criticalBuilder)->null;
        Member reporter, fixer, assignee;

        reporter= memberRepository.findByMemberId(reporterId).orElse(null);
        fixer= memberRepository.findByMemberId(fixerId).orElse(null);
        assignee= memberRepository.findByMemberId(assigneeId).orElse(null);

        Project project=projectRepository.findById(projectId).orElse(null);
        if(project!=null)
            spec=spec.and(IssueSpecification.findByProjectId(project));

        if(reporter!=null)
            spec=spec.and(IssueSpecification.findByReporter(reporter));
        if(fixer!=null)
            spec=spec.and(IssueSpecification.findByFixer(fixer));
        if(assignee!=null)
            spec=spec.and(IssueSpecification.findByAssignee(assignee));

        if(status!=null)
            spec=spec.and(IssueSpecification.findByStatus(status));
        if(priority!=null)
            spec=spec.and(IssueSpecification.findByPriority(priority));
        if(category!=null)
            spec=spec.and(IssueSpecification.findByCategory(category));
        return issueRepository.findAll(spec,sorting);
    }

    public Issue updateIssueState(Long id,Long issueId, Status status,String assigneeId){
        Issue issue=issueRepository.findById(issueId).orElse(null);
        if(issue==null)throw new IssueHandler(ErrorStatus.ISSUE_NOT_FOUND);

        switch(status){
            case ASSIGNED -> {
                if(assigneeId==null) throw new IssueHandler(ErrorStatus.ISSUE_ASSIGN_ASSIGNEE_ID_REQUIRED);

                Member assigner=memberRepository.findById(id).orElse(null);
                Member assignee=memberRepository.findByMemberId(assigneeId).orElse(null);
                if(assigner==null ||assignee==null)
                    throw new IssueHandler(ErrorStatus.MEMBER_NOT_FOUND);
                if(assigner.getRole()!=PL || assignee.getRole()!=DEV)
                    throw new IssueHandler(ErrorStatus.ISSUE_WRONG_ROLE_REQUEST);

                issue.updateAssignee(assignee);
                issue.updateStatus(Status.ASSIGNED);
            }
            case FIXED -> {
                Member fixer=memberRepository.findById(id).orElse(null);
                if(fixer==null)throw new IssueHandler(ErrorStatus.MEMBER_NOT_FOUND);
                if(fixer.getRole()!=DEV) throw new IssueHandler(ErrorStatus.ISSUE_WRONG_ROLE_REQUEST);

                issue.updateFixer(fixer);
                issue.updateStatus(Status.FIXED);

            }
            case RESOLVED -> {
                issue.updateStatus(Status.RESOLVED);
            }
            case CLOSED -> {
                issue.updateStatus(Status.CLOSED);
            }
            case REOPENED -> {
                issue.updateStatus(Status.REOPENED);
            }
            default -> {
                throw new IssueHandler(ErrorStatus.ISSUE_STATUS_BAD_REQUEST);
            }

        }
        return issue;
    }
    public void deleteIssue(Long memberId, Long issueId) {
        Issue issue=issueRepository.findById(issueId).orElse(null);
        if(issue==null)throw new IssueHandler(ErrorStatus.ISSUE_NOT_FOUND);

        issueRepository.delete(issue);
    }

    public IssueResponseDto.GetStatisticsResponseDto getIssueStatistics(int year, int month, Long projectId) {
        IssueResponseDto.GetStatisticsResponseDto response;

        List<Issue> issueList=issueRepository.findByProjectIdAndYearAndMonth(projectId, year,month);
        long issueCount=issueList.size();
        long[] issueCountByCategory =new long[5];
        for(Issue issue:issueList){
            int index=issue.getCategory().ordinal();
            issueCountByCategory[index]++;
        }

        return IssueResponseDto.GetStatisticsResponseDto.builder()
                .projectId(projectId)
                .year(year)
                .month(month)
                .issueCount(issueCount)
                .issueCountByCategory(issueCountByCategory)
                .build();
    }

    public ArrayList<String> getDevRecommend(long projectId, Category category){
        long[] devIdList= issueRepository.findDevByCategory(projectId,category.toString());
        ArrayList<String> devList=new ArrayList<>();
        for(int i=0;i<3;i++){
            if(i>devIdList.length-1) break;
            devList.add(memberRepository.findById(devIdList[i]).get().getMemberId());
        }
        return devList;
    }

}
