package SE_team.IssueManager.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.enums.Role;
import SE_team.IssueManager.payload.code.status.ErrorStatus;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.project.dto.FindMyProjectResponseDto.FindMyProjectRespDTO.ProjectInfo;
import SE_team.IssueManager.project.entity.Project;
import SE_team.IssueManager.project.entity.ProjectMember;
import SE_team.IssueManager.project.repository.ProjectMemberRepository;
import SE_team.IssueManager.project.repository.ProjectRepository;
import SE_team.IssueManager.repository.MemberRepository;
import jakarta.transaction.Transactional;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-data.properties")
class FindMyProjectServiceTest {
        @Autowired
        private MockMvc mockMvc;

        @Mock
        private ProjectMemberRepository projectMemberRepository;

        @Mock
        private MemberRepository memberRepository;

        @Mock
        private ProjectRepository projectRepository;

        @Autowired
        private ObjectMapper mapper;

        @Test
        @DisplayName("내 프로젝트 찾기 - 성공")
        void findMyProjects_Success() throws Exception {
                // Given
                String memberId = "user123";
                Long memberIdLong = 1L;

                Member member = Member.builder()
                                .id(memberIdLong)
                                .memberId(memberId)
                                .pw("password")
                                .role(Role.DEV)
                                .build();
                memberRepository.save(member);

                // 프로젝트 저장
                Project project1 = Project.builder().id(1L).name("Project A").build();
                projectRepository.save(project1);

                List<ProjectInfo> projectInfoList = new ArrayList<>();
                projectInfoList.add(ProjectInfo.builder().projectId(1L).projectName("Project A").build());
                projectInfoList.add(ProjectInfo.builder().projectId(2L).projectName("Project B").build());

                List<ProjectMember> projectMembers = new ArrayList<>();
                ProjectMember projectMember1 = ProjectMember.builder()
                                .project(project1)
                                .member(member)
                                .build();
                projectMemberRepository.save(projectMember1);

                Project project2 = Project.builder().id(2L).name("Project B").build();
                projectRepository.save(project2);

                ProjectMember projectMember2 = ProjectMember.builder()
                                .project(project2)
                                .member(member)
                                .build();
                projectMemberRepository.save(projectMember2);

                projectMembers.add(projectMember1);
                projectMembers.add(projectMember2);

                when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.of(member));
                when(projectMemberRepository.findProjectIdsByMemberId(memberIdLong)).thenReturn(projectMembers);
                when(projectRepository.findById(eq(1L)))
                                .thenReturn(Optional.of(Project.builder().id(1L).name("Project A").build()));
                when(projectRepository.findById(eq(2L)))
                                .thenReturn(Optional.of(Project.builder().id(2L).name("Project B").build()));
                // Then
                mockMvc.perform(get("/projects/{memberId}/check", memberId)
                                .param("memberId", memberId)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.code").value(SuccessStatus.PROJECT_FIND_OK.getCode()))
                                .andExpect(jsonPath("$.result.memberId").value(memberId))
                                .andExpect(jsonPath("$.result.projectInfoList").isArray())
                                .andExpect(jsonPath("$.result.projectInfoList[0].projectId").value(1))
                                .andExpect(jsonPath("$.result.projectInfoList[0].projectName").value("Project A"))
                                .andExpect(jsonPath("$.result.projectInfoList[1].projectId").value(2))
                                .andExpect(jsonPath("$.result.projectInfoList[1].projectName").value("Project B"));
        }

        @Test
        @DisplayName("내 프로젝트 찾기 - 회원 없음")
        void findMyProjects_MemberNotFound() throws Exception {
                // Given
                String memberId = "nonexistent";

                // When
                when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.empty());

                // Then
                mockMvc.perform(get("/projects/{memberId}/check", memberId)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.code").value(ErrorStatus.MEMBER_NOT_FOUND.getCode()));
        }
}
