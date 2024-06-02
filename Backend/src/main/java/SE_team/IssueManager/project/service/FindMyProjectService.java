package SE_team.IssueManager.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.ErrorStatus;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.payload.exception.handler.MemberHandler;
import SE_team.IssueManager.payload.exception.handler.ProjectHandler;
import SE_team.IssueManager.project.dto.FindMyProjectResponseDto.FindMyProjectRespDTO;
import SE_team.IssueManager.project.dto.FindMyProjectResponseDto.FindMyProjectRespDTO.ProjectInfo;
import SE_team.IssueManager.project.entity.Project;
import SE_team.IssueManager.project.entity.ProjectMember;
import SE_team.IssueManager.project.repository.ProjectMemberRepository;
import SE_team.IssueManager.project.repository.ProjectRepository;
import SE_team.IssueManager.repository.MemberRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class FindMyProjectService {
    private final ProjectMemberRepository projectMemberRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public FindMyProjectService(ProjectMemberRepository projectMemberRepository, MemberRepository memberRepository,
            ProjectRepository projectRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.memberRepository = memberRepository;
        this.projectRepository = projectRepository;
    }

    public ApiResponse<FindMyProjectRespDTO> findMyProjects(String memberId) {

        Member member = memberRepository.findByMemberId(memberId).orElse(null);
        if (member == null)
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        List<ProjectMember> projectMembers = projectMemberRepository.findProjectIdsByMemberId(member.getId());

        List<ProjectInfo> projectInfoList = new ArrayList<>();

        for (ProjectMember projectMember : projectMembers) {
            Project project = projectRepository.findById(projectMember.getProject().getId()).orElse(null);
            if (project == null)
                throw new ProjectHandler(ErrorStatus.PROJECT_NOT_FOUND);
            projectInfoList.add(new ProjectInfo(project.getId(), project.getName()));
        }
        FindMyProjectRespDTO result = new FindMyProjectRespDTO(memberId, projectInfoList);

        return ApiResponse.onSuccess(SuccessStatus.PROJECT_FIND_OK, result);
    }
}
