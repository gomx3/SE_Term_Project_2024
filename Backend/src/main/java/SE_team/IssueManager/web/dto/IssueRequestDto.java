package SE_team.IssueManager.web.dto;

import SE_team.IssueManager.domain.enums.Category;
import SE_team.IssueManager.domain.enums.Priority;
import SE_team.IssueManager.domain.enums.Status;
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
        private Long id;
        private String assigneeId;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateIssueStatusRequestDto {
        private Long id;    //회원 id
        private Status status;
        private String assigneeId;
    }

}
