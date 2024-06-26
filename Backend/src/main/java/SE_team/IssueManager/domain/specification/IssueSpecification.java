package SE_team.IssueManager.domain.specification;

import SE_team.IssueManager.domain.Issue;
import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.enums.Category;
import SE_team.IssueManager.domain.enums.Priority;
import SE_team.IssueManager.domain.enums.Status;
import SE_team.IssueManager.domain.Project;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class IssueSpecification {
    public static Specification<Issue> findByProjectId(Project project) {
        return new Specification<Issue>() {

            @Override
            public Predicate toPredicate(Root<Issue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("project"), project);
            }
        };
    }
    public static Specification<Issue> findByStatus(Status status){
        return new Specification<Issue>(){
            @Override
            public Predicate toPredicate(Root<Issue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("status"), status);
            }
        };
    }
    public static Specification<Issue> findByPriority(Priority priority){
        return new Specification<Issue>(){
            @Override
            public Predicate toPredicate(Root<Issue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("priority"), priority);
            }
        };
    }
    public static Specification<Issue> findByCategory(Category category){
        return new Specification<Issue>(){
            @Override
            public Predicate toPredicate(Root<Issue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("category"), category);
            }
        };
    }
    public static Specification<Issue> findByReporter(Member reporter){
        return new Specification<Issue>(){
            @Override
            public Predicate toPredicate(Root<Issue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("reporter"), reporter);
            }
        };
    }
    public static Specification<Issue> findByFixer(Member fixer){
        return new Specification<Issue>(){
            @Override
            public Predicate toPredicate(Root<Issue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("fixer"), fixer);
            }
        };
    }
    public static Specification<Issue> findByAssignee(Member assignee){
        return new Specification<Issue>(){
            @Override
            public Predicate toPredicate(Root<Issue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("assignee"), assignee);
            }
        };
    }
}
