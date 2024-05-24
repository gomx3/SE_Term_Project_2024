package SE_team.IssueManager.dto;

import SE_team.IssueManager.domain.enums.Category;
import SE_team.IssueManager.domain.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class IssueRequestDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateIssueRequestDto {
        private Long reporterId;
        private String title;
        private String description;
        private Priority priority;
        private Category category;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AssignIssueRequestDto {
        private Long assigneeId;
        private String fixerId;
    }

}
