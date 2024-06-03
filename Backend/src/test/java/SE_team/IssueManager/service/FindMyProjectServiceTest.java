package SE_team.IssueManager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import SE_team.IssueManager.payload.exception.handler.MemberHandler;
import SE_team.IssueManager.repository.MemberRepository;
import SE_team.IssueManager.repository.ProjectMemberRepository;
import SE_team.IssueManager.repository.ProjectRepository;
import SE_team.IssueManager.web.dto.FindMyProjectResponseDto.FindMyProjectRespDTO;
import jakarta.transaction.Transactional;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-data.properties")
class FindMyProjectServiceTest {
        @Mock
        private ProjectMemberRepository projectMemberRepository;

        @Mock
        private MemberRepository memberRepository;

        @Mock
        private ProjectRepository projectRepository;

        @InjectMocks
        private FindMyProjectService findMyProjectService;

        @Test
        @DisplayName("내 프로젝트 찾기 - 성공")
        void findMyProjects_Success() throws Exception {
                // Given
                String memberId = "user123";
                Member member = Member.builder()
                                .id(1L)
                                .memberId(memberId)
                                .pw("password")
                                .role(Role.DEV)
                                .build();
                when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.of(member));

                List<ProjectMember> projectMembers = new ArrayList<>();
                Project project1 = Project.builder().id(1L).name("Project A").build();
                Project project2 = Project.builder().id(2L).name("Project B").build();
                projectMembers.add(ProjectMember.builder().project(project1).member(member).build());
                projectMembers.add(ProjectMember.builder().project(project2).member(member).build());
                when(projectMemberRepository.findProjectIdsByMemberId(1L)).thenReturn(projectMembers);

                // When
                ApiResponse<FindMyProjectRespDTO> response = findMyProjectService.findMyProjects(memberId);

                // Then
                assertEquals(SuccessStatus.PROJECT_FIND_OK.getCode(), response.getCode());
                assertEquals(memberId, response.getResult().getMemberId());
                assertEquals(2, response.getResult().getProjectInfoList().size());
                assertEquals(1L, response.getResult().getProjectInfoList().get(0).getProjectId());
                assertEquals("Project A", response.getResult().getProjectInfoList().get(0).getProjectName());
                assertEquals(2L, response.getResult().getProjectInfoList().get(1).getProjectId());
                assertEquals("Project B", response.getResult().getProjectInfoList().get(1).getProjectName());
        }

        @Test
        @DisplayName("내 프로젝트 찾기 - 회원 없음")
        void findMyProjects_MemberNotFound() throws Exception {
                // Given
                String memberId = "nonexistent";
                when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.empty());

                // When & Then
                assertThrows(MemberHandler.class, () -> findMyProjectService.findMyProjects(memberId));
        }
}
