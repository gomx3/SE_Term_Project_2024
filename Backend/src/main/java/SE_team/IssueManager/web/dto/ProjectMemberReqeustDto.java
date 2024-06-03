package SE_team.IssueManager.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProjectMemberReqeustDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateProjectMemberRequestDTO {
        private String memberId;
    }

}
