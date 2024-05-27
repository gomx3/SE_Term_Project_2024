package SE_team.IssueManager.payload.exception.handler;

import SE_team.IssueManager.payload.code.BaseErrorCode;
import SE_team.IssueManager.payload.exception.GeneralException;

public class IssueHandler extends GeneralException {
    public IssueHandler(BaseErrorCode code) {
        super(code);
    }
}
