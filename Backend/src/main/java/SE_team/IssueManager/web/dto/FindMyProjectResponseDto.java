package SE_team.IssueManager.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FindMyProjectResponseDto {
    @Data
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindMyProjectRespDTO {
        private String memberId;
        private List<ProjectInfo> projectIds;

        public List<ProjectInfo> getProjectInfoList() {
            return projectIds;
        }

        @Data
        @Getter
        @Setter
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ProjectInfo {
            private Long projectId;
            private String projectName;
        }
    }
}
