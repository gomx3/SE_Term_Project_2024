package SE_team.IssueManager.dto;

import SE_team.IssueManager.domain.enums.Role;
import lombok.*;


public class MemberResponseDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SingUpRespDTO{  //회원가입 응답
        String memberId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetMemberInfoDTO{   //회원정보
        String memberId;
        Role role;
    }
}
