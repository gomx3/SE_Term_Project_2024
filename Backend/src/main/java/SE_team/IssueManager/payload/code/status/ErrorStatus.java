package SE_team.IssueManager.payload.code.status;

import SE_team.IssueManager.payload.code.BaseCode;
import SE_team.IssueManager.payload.code.BaseErrorCode;
import SE_team.IssueManager.payload.code.ErrorReasonDto;
import SE_team.IssueManager.payload.code.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    MEMBER_ID_EXISTS(HttpStatus.CONFLICT,"MEMBER_1000","이미 아이디가 존재합니다")
    ;

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
