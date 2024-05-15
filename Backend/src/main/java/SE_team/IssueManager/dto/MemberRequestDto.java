package SE_team.IssueManager.dto;

import SE_team.IssueManager.domain.enums.Role;
import lombok.*;


public class MemberRequestDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpReqDTO{   //회원가입 요청
        private String id;
        private String pw;
        private Role role;
    }
}
