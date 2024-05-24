package SE_team.IssueManager.controller;

import SE_team.IssueManager.domain.converter.IssueConverter;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.domain.Issue;
import SE_team.IssueManager.domain.enums.Category;
import SE_team.IssueManager.domain.enums.Priority;
import SE_team.IssueManager.domain.enums.Status;
import SE_team.IssueManager.dto.IssueRequestDto;
import SE_team.IssueManager.dto.IssueResponseDto;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/issues")
public class IssueController {
    private final IssueService issueService;

    @Autowired
    IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    //이슈 등록
    @PostMapping("/projects/{projectId}")
    public ApiResponse<IssueResponseDto.CreateIssueResponseDto> createIssue(
            @PathVariable(name="projectId") Long projectId,
            @RequestBody IssueRequestDto.CreateIssueRequestDto createIssueRequestDto
    ){
        Issue issue=issueService.createIssue(createIssueRequestDto);
        IssueResponseDto.CreateIssueResponseDto resp=IssueResponseDto.CreateIssueResponseDto.builder()
                .issueId(issue.getId())
                .createdAt(issue.getCreatedAt())
                .build();
        return ApiResponse.onSuccess(SuccessStatus.Issue_OK,resp);
    }

    //이슈 조회(검색 가능)
    @GetMapping("/projects/{projectId}")
    public ApiResponse<IssueResponseDto.GetIssueResponseDto> getIssue(
            @PathVariable (name = "projectId")Long projectId,
            @RequestParam(required=false,name = "reporterId") String reporterId,
            @RequestParam(required=false,name="fixerId") String fixerId,
            @RequestParam(required = false,name="assigneeId") String assigneeId,
            @RequestParam(required = false,name = "status") Status status,
            @RequestParam(required = false,name = "priority") Priority priority,
            @RequestParam(required = false,name = "category") Category category
    ) {
        List<Issue> issueList=issueService.findByCondition(reporterId,fixerId,assigneeId,status,priority,category);

        return ApiResponse.onSuccess(SuccessStatus.Issue_OK,
                IssueConverter.toIssueDtoList(issueList));
    }
}
