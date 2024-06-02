package SE_team.IssueManager.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProjectRequestDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateProjectRequestDTO {
        private String name;
        private String creatorId; // 생성자 ID 추가

    }
}
