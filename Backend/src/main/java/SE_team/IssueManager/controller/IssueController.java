package SE_team.IssueManager.controller;

import SE_team.IssueManager.ApiResponse;
import SE_team.IssueManager.domain.Issue;
import SE_team.IssueManager.domain.enums.Category;
import SE_team.IssueManager.domain.enums.Priority;
import SE_team.IssueManager.domain.enums.Status;
import SE_team.IssueManager.dto.IssueRequestDto;
import SE_team.IssueManager.dto.IssueResponseDto;
import SE_team.IssueManager.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;

    @PostMapping("/projects/{projectId}")
    public ApiResponse<IssueResponseDto.CreateIssueResponseDto> createIssue(
            @PathVariable Long projectId,
            @RequestBody IssueRequestDto.CreateIssueRequestDto createIssueRequestDto
    ){
        Issue issue=issueService.createIssue(createIssueRequestDto);
        IssueResponseDto.CreateIssueResponseDto resp=IssueResponseDto.CreateIssueResponseDto.builder()
                .issueId(issue.getId()).build();
        return ApiResponse.created(resp);
    }

    @GetMapping("/projects/{projectId}")
    public ApiResponse<IssueResponseDto.GetIssueResponseDto> getIssue(
            @PathVariable Long projectId,
            @RequestParam(required=false,name = "reporterId") String reporterId,
            @RequestParam(required=false,name="fixerId") String fixerId,
            @RequestParam(required = false,name="assigneeId") String assigneeId,
            @RequestParam(required = false,name = "status") Status status,
            @RequestParam(required = false,name = "priority") Priority priority,
            @RequestParam(required = false,name = "category") Category category
    ) {
        List<Issue> issueList=issueService.findByCondition(reporterId,fixerId,assigneeId,status,priority,category);
        IssueResponseDto.GetIssueResponseDto resp=new IssueResponseDto.GetIssueResponseDto();
        return ApiResponse.created(resp);
    }
}
