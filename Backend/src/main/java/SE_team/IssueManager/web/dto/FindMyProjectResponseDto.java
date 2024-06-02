package SE_team.IssueManager.web.dto;

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
        private List<ProjectInfo> projectIds;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ProjectInfo {
            private Long projectId;
            private String projectName;
        }
    }
}
