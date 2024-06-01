package SE_team.IssueManager.project.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FindMyProjectResponseDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindMyProjectRespDTO {
        private String memberId;
        private List<ProjectIds> projectIds;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ProjectIds {
            private Long projectId;
            private String projectName;
        }
    }
}
