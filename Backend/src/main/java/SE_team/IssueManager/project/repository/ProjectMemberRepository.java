package SE_team.IssueManager.project.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import SE_team.IssueManager.project.entity.ProjectMember;

@Repository
public interface ProjectMemberRepository extends CrudRepository<ProjectMember, Long> {
    @Query("SELECT m.id FROM Member m")
    Set<String> getMemberIds();
}