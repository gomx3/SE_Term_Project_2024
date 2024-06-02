package SE_team.IssueManager.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FindMyProjectResponseDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindMyProjectRespDTO {
        private String memberId;
        private List<ProjectInfo> projectIds;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ProjectInfo {
            private Long projectId;
            private String projectName;
        }
    }
}
