package SE_team.IssueManager.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FindMyPorjectRequest {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FineMyProjectReqDTO {
        private String memberId;
    }
}