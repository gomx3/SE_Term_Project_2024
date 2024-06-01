package SE_team.IssueManager.project.controller;

import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.project.dto.ProjectMemberResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.project.dto.ProjectMemberReqeustDto.CreateProjectMemberRequestDTO;
import SE_team.IssueManager.project.dto.ProjectMemberResponseDto.ProjectMemberDTO;
import SE_team.IssueManager.project.service.ProjectMemberService;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/members")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @Autowired
    public ProjectMemberController(ProjectMemberService projectMemberServcie) {
        this.projectMemberService = projectMemberServcie;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectMemberDTO>> addProjectMember(@PathVariable(name="projectId") Long projectId,
            @RequestBody CreateProjectMemberRequestDTO request) {
        String memberId = request.getMemberId();
        ApiResponse<ProjectMemberDTO> response = projectMemberService.addMemberToProject(projectId, memberId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dev")
    public ApiResponse<ProjectMemberResponseDto.ProjectDevDto> getProjectDevs(@PathVariable(name="projectId") Long projectId) {
        List<String> devList=projectMemberService.getProjectDevList(projectId);

        ProjectMemberResponseDto.ProjectDevDto response=ProjectMemberResponseDto.ProjectDevDto.builder()
                .devList(devList).build();
        return ApiResponse.onSuccess(SuccessStatus.PROJECT_OK,response);
    }
}