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
import SE_team.IssueManager.repository.IssueRepository;
import SE_team.IssueManager.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {
    private final IssueService issueService;

    @Autowired
    IssueController(IssueService issueService, IssueRepository issueRepository) {
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
        return ApiResponse.onSuccess(SuccessStatus.ISSUE_OK,resp);
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

        return ApiResponse.onSuccess(SuccessStatus.ISSUE_OK,
                IssueConverter.toIssueDtoList(issueList));
    }

//    @PatchMapping("/{issueId}/assign")
//    public ApiResponse<IssueResponseDto.AssignIssueResponseDto> assignIssue(
//            @PathVariable(name="issueId")Long issueId,
//            @RequestBody IssueRequestDto.AssignIssueRequestDto assignIssueRequestDto
//    ){
//        Issue updatedIssue=issueService.assignIssue(assignIssueRequestDto,issueId);
//        IssueResponseDto.AssignIssueResponseDto response=IssueResponseDto.AssignIssueResponseDto.builder()
//                .issueId(issueId)
//                .assignerId(updatedIssue.getAssignee().getId())
//                .assigneeId(updatedIssue.getFixer().getMemberId())
//                .build();
//
//        return ApiResponse.onSuccess(SuccessStatus.ISSUE_OK,response);
//    }

    @PatchMapping("/{issueId}")
    public ApiResponse<IssueResponseDto.UpdateIssueStatusResponseDto> updateIssueStatus(
            @PathVariable(name="issueId") Long issueId,
            @RequestBody IssueRequestDto.UpdateIssueStatusRequestDto requestDto
    ){
        Issue updatedIssue=issueService.updateIssueState(requestDto.getId(),issueId,requestDto.getStatus(),requestDto.getAssigneeId());
        IssueResponseDto.UpdateIssueStatusResponseDto response=IssueResponseDto.UpdateIssueStatusResponseDto.builder()
                .issueId(updatedIssue.getId())
                .status(updatedIssue.getStatus()).build();
        return ApiResponse.onSuccess(SuccessStatus.ISSUE_OK,response);
    }

    @DeleteMapping("/{issueId}")
    public ApiResponse<?> deleteIssue(
            @PathVariable (name="issueId") Long issueId,
            @RequestParam(name="memberId") Long memberId
    ){
        issueService.deleteIssue(memberId, issueId);
        return ApiResponse.onSuccess(SuccessStatus.ISSUE_OK,null);
    }

}
