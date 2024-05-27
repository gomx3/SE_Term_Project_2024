package SE_team.IssueManager.payload.exception;

import javax.naming.AuthenticationException;

public class LoginCheckFailException extends AuthenticationException {
    public LoginCheckFailException() {
        super("회원가입하지 않았거나 이메일 또는 비밀번호를 다시 확인해주세요.");
    }
}