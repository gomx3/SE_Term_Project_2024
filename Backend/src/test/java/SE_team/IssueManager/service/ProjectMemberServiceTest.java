package SE_team.IssueManager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.Project;
import SE_team.IssueManager.domain.enums.Role;
import SE_team.IssueManager.payload.ApiResponse;
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

        @Autowired
        private ProjectRepository projectRepository;

        @Autowired
        private MemberRepository memberRepository;

        @Autowired
        private ProjectMemberRepository projectMemberRepository;

        @Autowired
        private ProjectMemberService projectMemberService;

        private Project project;
        private Member member;

        @BeforeEach
        void setUp() {
                // Create a project
                project = Project.builder()
                                .name("Test Project")
                                .build();
                projectRepository.save(project);

                // Create a member
                member = Member.builder()
                                .memberId("user123")
                                .pw("password")
                                .role(Role.DEV)
                                .build();
                memberRepository.save(member);
        }

        @Test
        @DisplayName("프로젝트에 멤버 추가 - 성공")
        void addMemberToProject_Success() {
                // Given
                Long projectId = project.getId();
                String memberId = member.getMemberId();

                // When
                ApiResponse<ProjectMemberDTO> response = projectMemberService.addMemberToProject(projectId, memberId);

                // Then
                ProjectMemberDTO projectMemberDTO = response.getResult();
                assertEquals(projectId, projectMemberDTO.getProjectId());
                assertEquals("Test Project", projectMemberDTO.getName());
                assertTrue(projectMemberDTO.getMemberIds().contains(memberId));
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
