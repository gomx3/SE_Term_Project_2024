package SE_team.IssueManager.payload.exception;

import SE_team.IssueManager.payload.code.status.ErrorStatus;

public class LoginCheckFailException extends RuntimeException {
    private final ErrorStatus errorStatus;

    public LoginCheckFailException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }

    public ErrorStatus getErrorStatus() {
        return errorStatus;
    }
}