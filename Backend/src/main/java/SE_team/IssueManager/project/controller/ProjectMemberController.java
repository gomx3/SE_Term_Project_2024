package SE_team.IssueManager.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.project.dto.ProjectMemberReqeustDto.CreateProjectMemberRequestDTO;
import SE_team.IssueManager.project.dto.ProjectMemberResponseDto.ProjectMemberDTO;
import SE_team.IssueManager.project.service.ProjectMemberService;

@RestController
@RequestMapping("/projects/{projectId}/members")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @Autowired
    public ProjectMemberController(ProjectMemberService projectMemberServcie) {
        this.projectMemberService = projectMemberServcie;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectMemberDTO>> addProjectMember(@PathVariable Long projectId,
            @RequestBody CreateProjectMemberRequestDTO request) {
        String memberId = request.getMemberId();
        ApiResponse<ProjectMemberDTO> response = projectMemberService.addMemberToProject(projectId, memberId);
        return ResponseEntity.ok(response);
    }
}