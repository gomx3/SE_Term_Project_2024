package SE_team.IssueManager.project.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import SE_team.IssueManager.project.entity.ProjectMember;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    @Query("SELECT m.member.id FROM ProjectMember m WHERE m.project.id = :projectId")
    Set<String> findMemberIdsByProjectId(@Param("projectId")Long projectId);
}