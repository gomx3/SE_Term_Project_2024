package SE_team.IssueManager.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.project.dto.FindMyProjectResponseDto.FindMyProjectRespDTO;
import SE_team.IssueManager.project.service.FindMyProjectService;

@RestController
@RequestMapping("/projects")
public class FindMyProjectController {
    private final FindMyProjectService myProjectService;

    @Autowired
    public FindMyProjectController(FindMyProjectService myProjectService) {
        this.myProjectService = myProjectService;
    }

    @GetMapping("/{memberId}/check")
    public ResponseEntity<ApiResponse<FindMyProjectRespDTO>> findMyProjects(
            @PathVariable String memberId) {

        ApiResponse<FindMyProjectRespDTO> response = myProjectService.findMyProjects(memberId);
        return ResponseEntity.ok(response);
    }
}
