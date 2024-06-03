package SE_team.IssueManager.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
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
import SE_team.IssueManager.domain.Project;
import SE_team.IssueManager.domain.ProjectMember;
import SE_team.IssueManager.domain.enums.Role;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.repository.MemberRepository;
import SE_team.IssueManager.repository.ProjectMemberRepository;
import SE_team.IssueManager.repository.ProjectRepository;
import SE_team.IssueManager.web.dto.ProjectRequestDto.CreateProjectRequestDTO;
import SE_team.IssueManager.web.dto.ProjectResponseDto.ProjectDTO;
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

        @BeforeEach
        void setUp() {

                // 프로젝트 생성
                Project project = Project.builder()
                                .name("Test Project")
                                .build();
                projectRepository.save(project);

                // 멤버 생성
                Member creator = Member.builder()
                                .memberId("user123")
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
        }

        @Test
        @DisplayName("프로젝트 생성 - 성공")
        void createProject_Success() throws Exception {
                CreateProjectRequestDTO request = CreateProjectRequestDTO.builder()
                                .name("Test Project")
                                .creatorId("user123")
                                .build();

                ApiResponse<ProjectDTO> response = projectService.createProject(request);

                // Then
                mockMvc.perform(post("/projects")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.result.id").value(3L)) // 프로젝트 일련번호가 올바른지 확인
                                .andExpect(jsonPath("$.result.name").value("Test Project")); // 프로젝트 이름이 올바른지 확인
        }
}
