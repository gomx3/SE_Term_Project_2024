package SE_team.IssueManager.web.dto;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProjectMemberResponseDto {
    @Data
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectMemberDTO {
        private Long id;
        private Long projectId;
        private String name;
        private Set<String> memberIds;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ProjectDevDto {
        private List<String> devList;
    }

}
