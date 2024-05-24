package SE_team.IssueManager.dto;

import SE_team.IssueManager.domain.Issue;
import SE_team.IssueManager.domain.enums.Category;
import SE_team.IssueManager.domain.enums.Priority;
import SE_team.IssueManager.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class IssueResponseDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateIssueResponseDto {    //이슈 등록
        private Long issueId;
        private LocalDateTime createdAt;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetIssueDto{
        private Long issueId;
        private String title;
        private String description;
        private String reporter;
        private String fixer;
        private String assignee;
        private Status status;
        private Priority priority;
        private Category category;
        private LocalDateTime createdAt;

    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetIssueResponseDto {   //이슈 조회
        private List<GetIssueDto> issueList;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AssignIssueResponseDto {
        private Long issueId;
        private Long assigneeId;
        private String fixerId;
    }
}
