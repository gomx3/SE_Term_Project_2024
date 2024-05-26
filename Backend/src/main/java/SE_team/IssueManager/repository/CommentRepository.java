package SE_team.IssueManager.repository;

import SE_team.IssueManager.domain.Comment;
import SE_team.IssueManager.domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findByIssue(Issue issue);
}
