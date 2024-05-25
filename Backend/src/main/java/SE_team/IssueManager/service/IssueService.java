package SE_team.IssueManager.service;

import SE_team.IssueManager.domain.Issue;
import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.enums.Category;
import SE_team.IssueManager.domain.enums.Priority;
import SE_team.IssueManager.domain.enums.Role;
import SE_team.IssueManager.domain.enums.Status;
import SE_team.IssueManager.dto.IssueRequestDto;
import SE_team.IssueManager.repository.IssueRepository;
import SE_team.IssueManager.repository.MemberRepository;
import SE_team.IssueManager.specification.IssueSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IssueService {

    @Autowired
    private final IssueRepository issueRepository;
    @Autowired
    private final MemberRepository memberRepository;



    public Issue createIssue(IssueRequestDto.CreateIssueRequestDto request) {
        Issue issue=Issue.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .category(request.getCategory())
                .build();
        Issue savedIssue= issueRepository.save(issue);

        Member reporter=memberRepository.findById(request.getReporterId()).orElse(null);
        savedIssue.setReporter(reporter);


        return savedIssue;
    }

//    public List<Issue> findIssueByReporter(String reporterId){
//        Member reporter=memberRepository.findByMemberId(reporterId).get();
//        return issueRepository.findByReporterOrderByCreatedAtDesc(reporter);
//    }
//
//    public List<Issue> findIssueByDate(LocalDate date){
//        return issueRepository.findByCreatedDateOrderByCreatedAtDesc(date);
//    }
//
//    public List<Issue> findIssueByStatus(Status status){
//        return issueRepository.findByStatusOrderByCreatedAtDesc(status);
//    }
//
//    public List<Issue> findIssueByCategory(Category category){
//        return issueRepository.findByCategoryOrderByCreatedAtDesc(category);
//    }

    public List<Issue> findByCondition(String reporterId, String fixerId, String assigneeId, Status status, Priority priority, Category category ){
        Sort sorting=Sort.by(Sort.Order.desc("createdAt"));
        Specification<Issue> spec = (root,query,criticalBuilder)->null;
        Member reporter, fixer, assignee;

        reporter= memberRepository.findByMemberId(reporterId).orElse(null);
        fixer= memberRepository.findByMemberId(fixerId).orElse(null);
        assignee= memberRepository.findByMemberId(assigneeId).orElse(null);

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

    public Issue assignIssue(IssueRequestDto.AssignIssueRequestDto request, Long issueId) {
        Member assigner=memberRepository.findById(request.getId()).orElse(null);
        Member assignee=memberRepository.findByMemberId(request.getAssigneeId()).orElse(null);

        if(assigner!=null&&assignee!=null&&assigner.getRole()== Role.PL && assignee.getRole()==Role.DEV){
            Issue issue=issueRepository.findById(issueId).orElse(null);
            if(issue!=null){
                issue.setAssignee(assignee);
                issue.setStatus(Status.ASSIGNED);
                return issue;
            }
        }
        return null;
    }

    public Issue updateIssueState(Long id,Long issueId, Status status,String assigneeId){
        Issue issue=issueRepository.findById(issueId).orElse(null);
        if(issue!=null){
            switch(status){
                case ASSIGNED -> {
                    Member assignee=memberRepository.findByMemberId(assigneeId).orElse(null);
                    if(assignee!=null && assignee.getRole()== Role.DEV){
                        issue.setAssignee(assignee);
                        issue.setStatus(Status.ASSIGNED);
                    }
                }
                case FIXED -> {
                    Member fixer=memberRepository.findById(id).orElse(null);
                    if(fixer!=null && fixer.getRole()== Role.DEV){
                        issue.setFixer(fixer);
                        issue.setStatus(Status.FIXED);
                    }
                }
                case RESOLVED -> {
                    issue.setStatus(Status.RESOLVED);
                }
                case CLOSED -> {
                    issue.setStatus(Status.CLOSED);
                }
                case REOPENED -> {
                    issue.setStatus(Status.REOPENED);
                }
            }
            return issue;
        }
        return null;
    }
    public void deleteIssue(Long memberId, Long issueId) {
        Issue issue=issueRepository.findById(issueId).orElse(null);
        if(issue!=null){
            issueRepository.delete(issue);
        }
    }

}
