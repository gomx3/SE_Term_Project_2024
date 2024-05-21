package SE_team.IssueManager.dto;

import SE_team.IssueManager.domain.enums.Role;
import lombok.*;


public class MemberResponseDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SingUpRespDTO{  //회원가입 응답
        long id;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetMemberInfoDTO{   //회원정보
        long id;
        String memberId;
        Role role;
    }
}
