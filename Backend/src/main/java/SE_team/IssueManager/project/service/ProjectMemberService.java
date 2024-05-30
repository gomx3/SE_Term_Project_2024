package SE_team.IssueManager.project.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.project.dto.ProjectMemberReqeustDto.CreateProjectMemberRequestDTO;
import SE_team.IssueManager.project.dto.ProjectMemberResponseDto.ProjectMemberDTO;
import SE_team.IssueManager.project.entity.Project;
import SE_team.IssueManager.project.entity.ProjectMember;
import SE_team.IssueManager.project.repository.ProjectMemberRepository;
import SE_team.IssueManager.project.repository.ProjectRepository;
import SE_team.IssueManager.repository.MemberRepository;
import SE_team.IssueManager.service.MemberService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjectMemberService {

        private ProjectMemberRepository projectMemberRepository;
        private final ProjectRepository projectRepository;
        private final MemberRepository memberRepository;
        private final MemberService memberService;

        @Autowired
        public ProjectMemberService(ProjectMemberRepository projectMemberRepository,
                        ProjectRepository projectRepository,
                        MemberRepository memberRepository,
                        MemberService memberService) {
                this.projectMemberRepository = projectMemberRepository;
                this.projectRepository = projectRepository;
                this.memberRepository = memberRepository;
                this.memberService = memberService;
        }

        public ApiResponse<ProjectMemberDTO> addMemberToProject(Long projectId, CreateProjectMemberRequestDTO request) {

                // 프로젝트가 존재하는지 확인
                Project project = projectRepository.findById(projectId)
                                .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다: " + projectId));

                // 요청에서 전달된 멤버 ID들을 사용하여 멤버를 조회하고 유효성을 검사합니다.
                Set<Member> membersToAdd = memberRepository.findByMemberIdIn(request.getMemberIds()).stream()
                                .collect(Collectors.toSet());

                // 유효한 멤버만 추가하도록 필터링합니다.
                memberService.validateMembersExist(request.getMemberIds());

                ProjectMember projectMember = new ProjectMember();
                projectMember.setProjectId(projectId);
                projectMember.setMembers(membersToAdd);
                projectMemberRepository.save(projectMember);

                // 응답을 생성하여 반환합니다.
                ProjectMemberDTO projectMemberDTO = new ProjectMemberDTO(project.getId(), project.getName(),
                                membersToAdd.stream().map(Member::getMemberId).collect(Collectors.toSet()));
                return ApiResponse.onSuccess(SuccessStatus.MEMBER_ADD_OK, projectMemberDTO);
        }

}