package SE_team.IssueManager.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String memberId;
    private String pw;
}
