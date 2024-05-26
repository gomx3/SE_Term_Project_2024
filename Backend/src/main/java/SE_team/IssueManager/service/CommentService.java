package SE_team.IssueManager.service;

import SE_team.IssueManager.domain.Comment;
import SE_team.IssueManager.domain.Issue;
import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.dto.CommentRequestDto;
import SE_team.IssueManager.payload.code.status.ErrorStatus;
import SE_team.IssueManager.payload.exception.handler.IssueHandler;
import SE_team.IssueManager.payload.exception.handler.MemberHandler;
import SE_team.IssueManager.repository.CommentRepository;
import SE_team.IssueManager.repository.IssueRepository;
import SE_team.IssueManager.repository.MemberRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final MemberRepository memberRepository;

    public Comment createComment(CommentRequestDto.CreateCommentDto request, Long issueId) {
        Issue issue=issueRepository.findById(issueId).orElse(null);
        if(issue==null)
            throw new IssueHandler(ErrorStatus.ISSUE_NOT_FOUND);
        Member writer=memberRepository.findById(request.getId()).orElse(null);
        if(writer==null)
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        Comment comment=Comment.builder()
                .content(request.getContent())
                .writer(writer)
                .issue(issue).build();
        return commentRepository.save(comment);
    }

    public List<Comment> getComments(Long issueId){
        Issue issue=issueRepository.findById(issueId).orElse(null);
        if(issue==null) throw new IssueHandler(ErrorStatus.ISSUE_NOT_FOUND);

        return commentRepository.findByIssue(issue);
    }
}
