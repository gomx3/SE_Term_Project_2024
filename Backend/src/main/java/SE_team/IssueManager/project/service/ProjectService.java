package SE_team.IssueManager.project.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.project.dto.ProjectRequestDto.CreateProjectRequestDTO;
import SE_team.IssueManager.project.dto.ProjectResonseDto.ProjectDTO;
import SE_team.IssueManager.project.entity.Project;
import SE_team.IssueManager.project.repository.ProjectRepository;
import SE_team.IssueManager.repository.MemberRepository;
import SE_team.IssueManager.service.MemberService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjectService {

        private final ProjectRepository projectRepository;
        private final MemberRepository memberRepository;
        private final MemberService memberService;

        @Autowired
        public ProjectService(ProjectRepository projectRepository, MemberRepository memberRepository,
                        MemberService memberService) {
                this.projectRepository = projectRepository;
                this.memberRepository = memberRepository;
                this.memberService = memberService;
        }

        public ApiResponse<ProjectDTO> createProject(CreateProjectRequestDTO request) {
                memberService.validateMembersExist(request.getInitialMemberIds());

                Set<Member> initialMembers = memberRepository.findByMemberIdIn(request.getInitialMemberIds()).stream()
                                .collect(Collectors.toSet());

                Project project = Project.builder()
                                .name(request.getName())
                                .description(request.getDescription())
                                .members(initialMembers)
                                .build();

                Project savedProject = projectRepository.save(project);

                Set<String> initialMemberIds = initialMembers.stream().map(Member::getMemberId)
                                .collect(Collectors.toSet());

                ProjectDTO projectDTO = new ProjectDTO(savedProject.getId(), savedProject.getName(),
                                savedProject.getDescription(),
                                initialMemberIds);
                return ApiResponse.onSuccess(SuccessStatus.PROJECT_OK, projectDTO);
        }
}