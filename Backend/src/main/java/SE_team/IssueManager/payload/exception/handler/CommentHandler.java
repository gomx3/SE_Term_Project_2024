package SE_team.IssueManager.payload.exception.handler;

import SE_team.IssueManager.payload.code.BaseErrorCode;
import SE_team.IssueManager.payload.exception.GeneralException;

public class CommentHandler extends GeneralException {
    public CommentHandler(BaseErrorCode code) {
        super(code);
    }
}
