package SE_team.IssueManager.payload.exception.handler;

import SE_team.IssueManager.payload.code.BaseErrorCode;
import SE_team.IssueManager.payload.exception.GeneralException;

public class ProjectHandler extends GeneralException {
    public ProjectHandler(BaseErrorCode code) {
        super(code);
    }
}
