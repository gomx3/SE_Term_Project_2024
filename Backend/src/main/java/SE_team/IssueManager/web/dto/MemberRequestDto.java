package SE_team.IssueManager.web.dto;

import SE_team.IssueManager.domain.enums.Role;
import lombok.*;


public class MemberRequestDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpRequestDTO {   //회원가입 요청
        private String memberId;
        private String pw;
        private Role role;
    }
}
