package SE_team.IssueManager.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import SE_team.IssueManager.domain.Issue;
import SE_team.IssueManager.domain.converter.IssueConverter;
import SE_team.IssueManager.domain.enums.Category;
import SE_team.IssueManager.domain.enums.Priority;
import SE_team.IssueManager.domain.enums.Status;
import SE_team.IssueManager.web.dto.IssueRequestDto;
import SE_team.IssueManager.web.dto.IssueResponseDto;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.service.IssueService;

@RestController
@RequestMapping("/issues")

public class IssueController {
    private final IssueService issueService;

    @Autowired
    IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    // 이슈 등록
    @PostMapping("/projects/{projectId}")
    public ApiResponse<IssueResponseDto.CreateIssueResponseDto> createIssue(
            @PathVariable(name = "projectId") Long projectId,
            @RequestBody IssueRequestDto.CreateIssueRequestDto createIssueRequestDto) {
        Issue issue = issueService.createIssue(projectId, createIssueRequestDto);
        IssueResponseDto.CreateIssueResponseDto resp = IssueResponseDto.CreateIssueResponseDto.builder()
                .issueId(issue.getId())
                .createdAt(issue.getCreatedAt())
                .build();
        return ApiResponse.onSuccess(SuccessStatus.ISSUE_OK, resp);
    }

    // 이슈 조회(검색 가능)
    @GetMapping("/projects/{projectId}")
    public ApiResponse<IssueResponseDto.GetIssueResponseDto> getIssue(
            @PathVariable(name = "projectId") Long projectId,
            @RequestParam(required = false, name = "reporterId") String reporterId,
            @RequestParam(required = false, name = "fixerId") String fixerId,
            @RequestParam(required = false, name = "assigneeId") String assigneeId,
            @RequestParam(required = false, name = "status") Status status,
            @RequestParam(required = false, name = "priority") Priority priority,
            @RequestParam(required = false, name = "category") Category category) {
        List<Issue> issueList = issueService.findByCondition(projectId, reporterId, fixerId, assigneeId, status,
                priority, category);
        return ApiResponse.onSuccess(SuccessStatus.ISSUE_OK,
                IssueConverter.toIssueDtoList(issueList));
    }

    @PatchMapping("/{issueId}")
    public ApiResponse<IssueResponseDto.UpdateIssueStatusResponseDto> updateIssueStatus(
            @PathVariable(name = "issueId") Long issueId,
            @RequestBody IssueRequestDto.UpdateIssueStatusRequestDto requestDto) {
        Issue updatedIssue = issueService.updateIssueState(requestDto.getId(), issueId, requestDto.getStatus(),
                requestDto.getAssigneeId());
        IssueResponseDto.UpdateIssueStatusResponseDto response = IssueResponseDto.UpdateIssueStatusResponseDto.builder()
                .issueId(updatedIssue.getId())
                .status(updatedIssue.getStatus()).build();
        return ApiResponse.onSuccess(SuccessStatus.ISSUE_OK, response);
    }

    @DeleteMapping("/{issueId}")
    public ApiResponse<?> deleteIssue(
            @PathVariable(name = "issueId") Long issueId,
            @RequestParam(name = "memberId") Long memberId) {
        issueService.deleteIssue(memberId, issueId);
        return ApiResponse.onSuccess(SuccessStatus.ISSUE_OK, null);
    }

    @GetMapping("/projects/{projectId}/statistics")
    public ApiResponse<IssueResponseDto.GetStatisticsResponseDto> getStatistics(
            @PathVariable(name = "projectId") Long projectId,
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month) {
        IssueResponseDto.GetStatisticsResponseDto response = issueService.getIssueStatistics(year, month, projectId);
        return ApiResponse.onSuccess(SuccessStatus.ISSUE_OK, response);
    }

    @GetMapping("/projects/{projectId}/recommend")
    public ApiResponse<IssueResponseDto.GetDevRecommend> getDevRecommend(
            @PathVariable(name = "projectId") Long projectId,
            @RequestParam(name = "category") Category category) {
        ArrayList<String> devList = issueService.getDevRecommend(projectId, category);
        IssueResponseDto.GetDevRecommend response = IssueResponseDto.GetDevRecommend.builder()
                .length(devList.size())
                .devList(devList).build();
        return ApiResponse.onSuccess(SuccessStatus.ISSUE_OK, response);
    }

}
