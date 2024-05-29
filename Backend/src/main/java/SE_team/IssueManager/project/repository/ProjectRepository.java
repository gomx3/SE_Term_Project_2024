package SE_team.IssueManager.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SE_team.IssueManager.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}