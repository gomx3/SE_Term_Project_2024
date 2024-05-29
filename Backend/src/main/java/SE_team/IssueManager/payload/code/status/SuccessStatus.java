package SE_team.IssueManager.payload.code.status;

import org.springframework.http.HttpStatus;

import SE_team.IssueManager.payload.code.BaseCode;
import SE_team.IssueManager.payload.code.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    ISSUE_OK(HttpStatus.OK, "ISSUE_2000", "success"),
    MEMBER_OK(HttpStatus.OK, "MEMBER_1000", "success"),
    COMMENT_OK(HttpStatus.OK, "COMMENT_3000", "success"),
    SIGNUP_SUCCESS(HttpStatus.OK, "AUTH_1000", "signup success"),
    LOGOUT_SUCCESS(HttpStatus.OK, "AUTH_1002", "logout success"),
    LOGIN_SUCCESS(HttpStatus.OK, "AUTH_1001", "login success"),
    PROJECT_OK(HttpStatus.OK, "PROJECT_1000", "project success");

    // 로그아웃 석세스 만들기

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(true).build();
    }

    @Override
    public ReasonDto getReasonHttpStatus() {
        return ReasonDto.builder()
                .httpStatus(httpStatus)
                .message(message)
                .code(code)
                .isSuccess(true).build();
    }
}
