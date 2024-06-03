package SE_team.IssueManager.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FindMyPorjectRequest {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FineMyProjectReqDTO {
        private String memberId;
    }
}
