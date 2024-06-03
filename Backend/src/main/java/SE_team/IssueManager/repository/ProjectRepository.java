package SE_team.IssueManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SE_team.IssueManager.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}