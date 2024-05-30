package SE_team.IssueManager.project.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SE_team.IssueManager.project.entity.ProjectMember;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    @Query("SELECT pm.id FROM ProjectMember pm")
    Set<Long> getMemberIds();
}