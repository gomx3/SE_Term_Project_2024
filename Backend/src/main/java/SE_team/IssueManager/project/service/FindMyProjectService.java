package SE_team.IssueManager.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.project.dto.FindMyProjectResponseDto.FindMyProjectRespDTO;
import SE_team.IssueManager.project.dto.FindMyProjectResponseDto.FindMyProjectRespDTO.ProjectIds;
import SE_team.IssueManager.project.entity.Project;
import SE_team.IssueManager.project.repository.ProjectMemberRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class FindMyProjectService {
    private final ProjectMemberRepository projectMemberRepository;

    @Autowired
    public FindMyProjectService(ProjectMemberRepository projectMemberRepository) {
        this.projectMemberRepository = projectMemberRepository;
    }

    public ApiResponse<FindMyProjectRespDTO> findMyProjects(String memberId) {
        Long projectMemberId = projectMemberRepository.findIdByMemberId(memberId);

        List<Long> projects = projectMemberRepository.findProjectsByMemberId(projectMemberId);

        List<ProjectIds> projectList = new ArrayList<>();
        for (Long projectId : projects) {
            Project project = projectMemberRepository.findProjectById(projectId);
            if (project != null) {
                projectList.add(new ProjectIds(project.getId(), project.getName()));
            }
        }

        FindMyProjectRespDTO result = new FindMyProjectRespDTO(memberId, projectList);

        return ApiResponse.onSuccess(SuccessStatus.PROJECT_FIND_OK, result);
    }
}
