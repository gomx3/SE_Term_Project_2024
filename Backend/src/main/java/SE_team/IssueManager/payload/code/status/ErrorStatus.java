package SE_team.IssueManager.payload.code.status;

import org.springframework.http.HttpStatus;

import SE_team.IssueManager.payload.code.BaseErrorCode;
import SE_team.IssueManager.payload.code.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    INVALID_USERNAME(HttpStatus.UNAUTHORIZED, "MEMBER_1003", "Invalid username"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "MEMBER_1004", "invalid password"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "MEMBER_1005", "Invalid credentials"),
    MEMBER_ID_EXISTS(HttpStatus.CONFLICT, "MEMBER_1000", "id already exists"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_1001", "member not found"),
    MEMBER_BAD_REQUEST(HttpStatus.BAD_REQUEST, "MEMBER_1002", "bad request"),

    ISSUE_NOT_FOUND(HttpStatus.NOT_FOUND, "ISSUE_1000", "issue not found"),
    ISSUE_WRONG_ROLE_REQUEST(HttpStatus.BAD_REQUEST, "ISSUE_1001", "the role is not allowed to do the behavior"),
    ISSUE_ASSIGN_ASSIGNEE_ID_REQUIRED(HttpStatus.BAD_REQUEST, "ISSUE_1002", "assigneeId is required"),
    ISSUE_STATUS_BAD_REQUEST(HttpStatus.BAD_REQUEST, "ISSUE_1003", "bad request"),
    ISSUE_COMMENT_NOT_CREATED(HttpStatus.NOT_FOUND, "ISSUE_1004", "comment not created"),
    ISSUE_PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "ISSUE_1005", "project not found"),

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_1000", "comment not found"),

    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "PROJECT_1000", "project not found"),;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDto getReason() {
        return ErrorReasonDto.builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }

    @Override
    public ErrorReasonDto getReasonHttpStatus() {
        return ErrorReasonDto.builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .httpStatus(httpStatus)
                .build();
    }
}
