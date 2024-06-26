package SE_team.IssueManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import SE_team.IssueManager.domain.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.web.dto.ProjectMemberResponseDto.ProjectMemberDTO;
import SE_team.IssueManager.domain.Project;
import SE_team.IssueManager.domain.ProjectMember;
import SE_team.IssueManager.repository.ProjectMemberRepository;
import SE_team.IssueManager.repository.ProjectRepository;
import SE_team.IssueManager.repository.MemberRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjectMemberService {
        @Autowired
        private ProjectRepository projectRepository;

        @Autowired
        private MemberRepository memberRepository;

        @Autowired
        private ProjectMemberRepository projectMemberRepository;

        public ApiResponse<ProjectMemberDTO> addMemberToProject(Long projectId, String memberId) {
                Project project = projectRepository.findById(projectId)
                                .orElseThrow(() -> new RuntimeException("Project not found"));

                Member member = memberRepository.findByMemberId(memberId)
                                .orElseThrow(() -> new RuntimeException("Member not found"));

                ProjectMember projectMember = ProjectMember.builder()
                                .project(project)
                                .member(member)
                                .build();

                ProjectMember savedProjectMember = projectMemberRepository.save(projectMember);

                Set<String> existingMemberIds = projectMemberRepository.findMemberIdsByProjectId(projectId);

                ProjectMemberDTO projectMemberDTO = new ProjectMemberDTO(savedProjectMember.getId(),
                                project.getId(),
                                project.getName(),
                                existingMemberIds);

                projectMemberDTO.setId(projectId);
                projectMemberDTO.setName(project.getName());
                projectMemberDTO.setMemberIds(existingMemberIds);

                return ApiResponse.onSuccess(SuccessStatus.MEMBER_ADD_OK, projectMemberDTO);
        }

        public List<String> getProjectDevList(Long projectId) {
                List<Member> memberList=projectMemberRepository.findMembersByProjectId(projectId);
                List<String> devList=new ArrayList<>();
                for(Member member:memberList){
                        if(member.getRole()== Role.DEV)
                                devList.add(member.getMemberId());
                }
                return devList;
        }

}