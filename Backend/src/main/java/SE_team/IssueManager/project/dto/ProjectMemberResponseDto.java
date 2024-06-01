package SE_team.IssueManager.project.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProjectMemberResponseDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectMemberDTO {
        private Long id;
        private Long projectId;
        private String name;
        private Set<String> memberIds;
    }

}
