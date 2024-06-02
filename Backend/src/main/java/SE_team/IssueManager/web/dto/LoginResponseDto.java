package SE_team.IssueManager.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class LoginResponseDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRespDTO {
        private Long Id;
        private String memberId;
        private String role;
    }
}
