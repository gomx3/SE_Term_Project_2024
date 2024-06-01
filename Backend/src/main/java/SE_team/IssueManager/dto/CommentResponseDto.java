package SE_team.IssueManager.dto;

import SE_team.IssueManager.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class CommentResponseDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateCommentDto {
        private Long commentId;
        private LocalDateTime createdAt;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetCommentList{
        private List<GetCommentDto> commentList;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetCommentDto{
        private Long commentId;
        private String content;
        private MemberResponseDto.GetMemberInfoDTO writer;
        private LocalDateTime createdAt;
    }
}
