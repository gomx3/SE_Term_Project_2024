package SE_team.IssueManager.domain.converter;

import java.util.List;

import SE_team.IssueManager.domain.Issue;
import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.web.dto.IssueResponseDto;

public class IssueConverter {
    public static IssueResponseDto.GetIssueDto toIssueDto(Issue issue) {
        Member fixer = issue.getFixer();
        String fixerId = (fixer != null) ? fixer.getMemberId() : null;
        Member assignee = issue.getAssignee();
        String assigneeId = (assignee != null) ? assignee.getMemberId() : null;
        return IssueResponseDto.GetIssueDto.builder()
                .issueId(issue.getId())
                .title(issue.getTitle())
                .description(issue.getDescription())
                .reporter(issue.getReporter().getMemberId())
                .fixer(fixerId)
                .assignee(assigneeId)
                .status(issue.getStatus())
                .priority(issue.getPriority())
                .category(issue.getCategory())
                .createdAt(issue.getCreatedAt()).build();
    }

    public static IssueResponseDto.GetIssueResponseDto toIssueDtoList(List<Issue> issues) {
        List<IssueResponseDto.GetIssueDto> issueDtoList = issues.stream()
                .map(IssueConverter::toIssueDto)
                .toList();
        return IssueResponseDto.GetIssueResponseDto.builder()
                .issueList(issueDtoList).build();
    }
}
