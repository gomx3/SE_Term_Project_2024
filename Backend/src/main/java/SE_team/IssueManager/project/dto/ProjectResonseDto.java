package SE_team.IssueManager.project.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProjectResonseDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectDTO {
        private Long id;
        private String name;
        private String description;
        private Set<String> initialMembers;
    }
}
