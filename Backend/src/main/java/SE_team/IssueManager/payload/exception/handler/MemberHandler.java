package SE_team.IssueManager.payload.exception.handler;

import SE_team.IssueManager.payload.code.BaseErrorCode;
import SE_team.IssueManager.payload.exception.GeneralException;

public class MemberHandler extends GeneralException {
    public MemberHandler(BaseErrorCode code) {
        super(code);
    }

}
