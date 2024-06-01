package SE_team.IssueManager.project.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProjectMemberReqeustDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateProjectMemberRequestDTO {
        private Set<String> memberId;
    }

}
