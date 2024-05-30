package SE_team.IssueManager.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.project.dto.ProjectRequestDto.CreateProjectRequestDTO;
import SE_team.IssueManager.project.dto.ProjectResponseDto.ProjectDTO;
import SE_team.IssueManager.project.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectDTO>> createProject(@RequestBody CreateProjectRequestDTO request) {
        ApiResponse<ProjectDTO> response = projectService.createProject(request);
        return ResponseEntity.ok(response);
    }
}