package SE_team.IssueManager.project.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.project.dto.ProjectMemberReqeustDto.CreateProjectMemberRequestDTO;
import SE_team.IssueManager.project.dto.ProjectMemberResponseDto.ProjectMemberDTO;
import SE_team.IssueManager.project.repository.ProjectMemberRepository;
import SE_team.IssueManager.project.service.ProjectMemberService;

@RestController
@RequestMapping("/projects/{projectId}/members")
public class ProjectMemberController {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectMemberService projectMemberService;

    @Autowired
    public ProjectMemberController(ProjectMemberRepository projectMemberRepository,
            ProjectMemberService projectMemberServcie) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectMemberService = projectMemberServcie;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectMemberDTO>> addProjectMember(@PathVariable Long projectId,
            @RequestBody CreateProjectMemberRequestDTO request) {
        ApiResponse<ProjectMemberDTO> response = projectMemberService.addMemberToProject(projectId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ids")
    public ResponseEntity<Set<String>> getMemberIds(@PathVariable Long projectId) {
        Set<String> memberIds = projectMemberRepository.getMemberIds();
        return ResponseEntity.ok(memberIds);
    }
}