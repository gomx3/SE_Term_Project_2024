package SE_team.IssueManager.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.project.dto.ProjectRequestDto.CreateProjectRequestDTO;
import SE_team.IssueManager.project.dto.ProjectResponseDto.ProjectDTO;
import SE_team.IssueManager.project.entity.Project;
import SE_team.IssueManager.project.entity.ProjectMember;
import SE_team.IssueManager.project.repository.ProjectMemberRepository;
import SE_team.IssueManager.project.repository.ProjectRepository;
import SE_team.IssueManager.repository.MemberRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjectService {

        private final ProjectRepository projectRepository;
        private final MemberRepository memberRepository;
        private final ProjectMemberRepository projectMemberRepository;

        @Autowired
        public ProjectService(ProjectRepository projectRepository, MemberRepository memberRepository,
                        ProjectMemberRepository projectMemberRepository) {
                this.projectRepository = projectRepository;
                this.memberRepository = memberRepository;
                this.projectMemberRepository = projectMemberRepository;
        }

        public ApiResponse<ProjectDTO> createProject(CreateProjectRequestDTO request) {

                Project project = Project.builder()
                                .name(request.getName())
                                .build();

                Project savedProject = projectRepository.save(project);

                // 생성자를 프로젝트 멤버로 추가
                Member creator = memberRepository.findById(request.getCreatorId())
                                .orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다."));

                ProjectMember projectMember = ProjectMember.builder()
                                .project(savedProject)
                                .member(creator)
                                .build();
                projectMemberRepository.save(projectMember); // 프로젝트 멤버 저장

                ProjectDTO projectDTO = new ProjectDTO(savedProject.getId(), savedProject.getName());
                return ApiResponse.onSuccess(SuccessStatus.PROJECT_OK, projectDTO);
        }
}