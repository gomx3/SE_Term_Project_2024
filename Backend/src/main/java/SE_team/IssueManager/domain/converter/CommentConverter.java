package SE_team.IssueManager.domain.converter;

import SE_team.IssueManager.domain.Comment;
import SE_team.IssueManager.domain.Issue;
import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.dto.CommentResponseDto;
import SE_team.IssueManager.dto.IssueResponseDto;
import SE_team.IssueManager.dto.MemberResponseDto;

import java.util.List;

public class CommentConverter {
    public static CommentResponseDto.GetCommentDto toCommentDto(Comment comment) {
        Member writer=comment.getWriter();
        if (writer == null) {
            return null;
        }
        MemberResponseDto.GetMemberInfoDTO writerDto=MemberResponseDto.GetMemberInfoDTO.builder()
                .id(writer.getId())
                .memberId(writer.getMemberId())
                .role(writer.getRole()).build();
        return CommentResponseDto.GetCommentDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .writer(writerDto)
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static CommentResponseDto.GetCommentList toCommentList(List<Comment> comments) {
        List<CommentResponseDto.GetCommentDto> commnetDtoList = comments.stream()
                .map(CommentConverter::toCommentDto)
                .toList();
        return CommentResponseDto.GetCommentList.builder()
                .commentList(commnetDtoList).build();
    }
}
