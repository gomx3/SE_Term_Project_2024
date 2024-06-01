package SE_team.IssueManager.dto;

import SE_team.IssueManager.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberResponseDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SingUpRespDTO { // 회원가입 응답
        long id;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetMemberInfoDTO { // 회원정보
        long id;
        String memberId;
        Role role;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberSaveDTO {
        private String memberId;
        private String password;
        private Role role;
    }
}
