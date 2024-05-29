package SE_team.IssueManager.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProjectRequestDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateProjectRequestDTO {
        private String name;
        private String description;
    }
}
