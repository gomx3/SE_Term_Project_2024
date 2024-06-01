package SE_team.IssueManager.project.repository;

import java.util.List;
import java.util.Set;

import SE_team.IssueManager.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import SE_team.IssueManager.project.entity.Project;
import SE_team.IssueManager.project.entity.ProjectMember;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    @Query("SELECT m.member.id FROM ProjectMember m WHERE m.project.id = :projectId")
    Set<String> findMemberIdsByProjectId(@Param("projectId")Long projectId);

    @Query("SELECT m.member FROM ProjectMember m WHERE m.project.id=:projectId")
    List<Member> findMembersByProjectId(@Param("projectId")Long projectId);

    List<ProjectMember> findProjectIdsByMemberId(Long memberId);

    @Query("SELECT m.id FROM ProjectMember m WHERE m.member.memberId = :memberId")
    Long findIdByMemberId(String memberId);

    @Query("SELECT p FROM Project p WHERE p.id = :projectId")
    Project findProjectById(Long projectId);

}