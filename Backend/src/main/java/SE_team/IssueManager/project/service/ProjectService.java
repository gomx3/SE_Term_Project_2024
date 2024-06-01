package SE_team.IssueManager.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.project.dto.ProjectRequestDto.CreateProjectRequestDTO;
import SE_team.IssueManager.project.dto.ProjectResponseDto.ProjectDTO;
import SE_team.IssueManager.project.entity.Project;
import SE_team.IssueManager.project.repository.ProjectRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjectService {

        private final ProjectRepository projectRepository;

        @Autowired
        public ProjectService(ProjectRepository projectRepository) {
                this.projectRepository = projectRepository;
        }

        public ApiResponse<ProjectDTO> createProject(CreateProjectRequestDTO request) {

                Project project = Project.builder()
                                .name(request.getName())
                                .build();

                Project savedProject = projectRepository.save(project);

                ProjectDTO projectDTO = new ProjectDTO(savedProject.getId(), savedProject.getName());
                return ApiResponse.onSuccess(SuccessStatus.PROJECT_OK, projectDTO);
        }
}