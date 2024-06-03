package SE_team.IssueManager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.Project;
import SE_team.IssueManager.domain.ProjectMember;
import SE_team.IssueManager.domain.enums.Role;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.repository.MemberRepository;
import SE_team.IssueManager.repository.ProjectMemberRepository;
import SE_team.IssueManager.repository.ProjectRepository;
import SE_team.IssueManager.web.dto.ProjectMemberResponseDto.ProjectMemberDTO;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;

@AutoConfigureMockMvc
@SpringBootTest
@NoArgsConstructor
@Transactional
@TestPropertySource(locations = "classpath:application-data.properties")
class ProjectMemberServiceTest {

        @Mock
        private ProjectRepository projectRepository;

        @Mock
        private MemberRepository memberRepository;

        @Mock
        private ProjectMemberRepository projectMemberRepository;

        @InjectMocks
        private ProjectMemberService projectMemberService;

        private Project project;
        private Member member;

        @Test
        @DisplayName("프로젝트에 멤버 추가 - 성공")
        void addMemberToProject_Success() {
                // Given
                Long projectId = 1L;
                String memberId = "user123";

                Project project = Project.builder().id(projectId).name("Test Project").build();
                Member member = Member.builder().id(1L).memberId(memberId).build();
                ProjectMember projectMember = ProjectMember.builder().project(project).member(member).build();

                Set<String> existingMemberIds = new HashSet<>();
                existingMemberIds.add(memberId);

                // 리포지토리를 통해 프로젝트를 저장하도록 설정
                when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
                when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.of(member));
                when(projectMemberRepository.save(projectMember)).thenReturn(projectMember);
                when(projectMemberRepository.findMemberIdsByProjectId(projectId)).thenReturn(existingMemberIds);

                // When
                ApiResponse<ProjectMemberDTO> response = projectMemberService.addMemberToProject(projectId, memberId);

                // Then
                ProjectMemberDTO projectMemberDTO = response.getResult();
                assertEquals(SuccessStatus.MEMBER_ADD_OK.getCode(), response.getCode());
                assertEquals(projectId, projectMemberDTO.getProjectId());
                assertEquals("Test Project", projectMemberDTO.getName());
                assertEquals(existingMemberIds, projectMemberDTO.getMemberIds());
        }

        @Test
        @DisplayName("프로젝트에 속한 개발자 목록 조회")
        void getProjectDevList_Success() {
                // Given
                Long projectId = 1L;
                Member devMember1 = new Member();
                devMember1.setId(1L);
                devMember1.setMemberId("dev1");
                devMember1.setRole(Role.DEV);

                Member devMember2 = new Member();
                devMember2.setId(2L);
                devMember2.setMemberId("dev2");
                devMember2.setRole(Role.DEV);

                List<Member> memberList = new ArrayList<>();
                memberList.add(devMember1);
                memberList.add(devMember2);

                when(projectMemberRepository.findMembersByProjectId(projectId)).thenReturn(memberList);

                // When
                List<String> devList = projectMemberService.getProjectDevList(projectId);

                // Then
                assertEquals(2, devList.size());
                assertEquals("dev1", devList.get(0));
                assertEquals("dev2", devList.get(1));
        }
}
