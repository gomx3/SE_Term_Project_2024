package SE_team.IssueManager.controller;

import SE_team.IssueManager.domain.Comment;
import SE_team.IssueManager.domain.converter.CommentConverter;
import SE_team.IssueManager.dto.CommentRequestDto;
import SE_team.IssueManager.dto.CommentResponseDto;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.ErrorStatus;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.payload.exception.handler.IssueHandler;
import SE_team.IssueManager.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;

    @Autowired
    CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/issues/{issueId}")
    public ApiResponse<CommentResponseDto.CreateCommentDto> createComment(
            @PathVariable(name="issueId") Long issueId,
            @RequestBody CommentRequestDto.CreateCommentDto request
    ){
        Comment savedComment= commentService.createComment(request,issueId);
        if(savedComment==null)
            throw new IssueHandler(ErrorStatus.ISSUE_COMMENT_NOT_CREATED);
        CommentResponseDto.CreateCommentDto response =CommentResponseDto.CreateCommentDto.builder()
                .commentId(savedComment.getId())
                .createdAt(savedComment.getCreatedAt()).build();

        return ApiResponse.onSuccess(SuccessStatus.COMMENT_OK,response);
    }

    @GetMapping("/issues/{issueId}")
    public ApiResponse<CommentResponseDto.GetCommentList> getComments(
            @PathVariable(name="issueId") Long issueId
    ){
        List<Comment> commentList=commentService.getComments(issueId);

        return ApiResponse.onSuccess(SuccessStatus.COMMENT_OK, CommentConverter.toCommentList(commentList));
    }
}