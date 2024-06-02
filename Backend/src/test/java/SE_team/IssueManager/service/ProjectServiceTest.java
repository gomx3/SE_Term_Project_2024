package SE_team.IssueManager.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.enums.Role;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.project.dto.ProjectRequestDto.CreateProjectRequestDTO;
import SE_team.IssueManager.project.dto.ProjectResponseDto.ProjectDTO;
import SE_team.IssueManager.project.entity.Project;
import SE_team.IssueManager.project.entity.ProjectMember;
import SE_team.IssueManager.project.repository.ProjectMemberRepository;
import SE_team.IssueManager.project.repository.ProjectRepository;
import SE_team.IssueManager.project.service.ProjectService;
import SE_team.IssueManager.repository.MemberRepository;
import jakarta.transaction.Transactional;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-data.properties")
class ProjectServiceTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ProjectService projectService;

        @Autowired
        private ProjectRepository projectRepository;

        @Autowired
        private MemberRepository memberRepository;

        @Autowired
        private ProjectMemberRepository projectMemberRepository;

        @Test
        @DisplayName("프로젝트 생성 - 성공")
        void createProject_Success() throws Exception {
                // Given
                CreateProjectRequestDTO request = CreateProjectRequestDTO.builder()
                                .name("Test Project")
                                .creatorId("user123")
                                .build();

                // 프로젝트 생성
                Project project = Project.builder()
                                .name(request.getName())
                                .build();
                projectRepository.save(project);

                // 멤버 생성
                Member creator = Member.builder()
                                .memberId(request.getCreatorId())
                                .pw("password")
                                .role(Role.DEV)
                                .build();
                memberRepository.save(creator);

                // 프로젝트 멤버 생성
                ProjectMember projectMember = ProjectMember.builder()
                                .project(project)
                                .member(creator)
                                .build();
                projectMemberRepository.save(projectMember);

                ApiResponse<ProjectDTO> response = projectService.createProject(request);

                // Then
                mockMvc.perform(post("/projects")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
                                .andExpect(status().isOk());
        }
}
