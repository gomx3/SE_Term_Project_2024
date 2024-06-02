package SE_team.IssueManager.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.project.dto.ProjectMemberReqeustDto.CreateProjectMemberRequestDTO;
import SE_team.IssueManager.project.dto.ProjectMemberResponseDto;
import SE_team.IssueManager.project.dto.ProjectMemberResponseDto.ProjectDevDto;
import SE_team.IssueManager.project.service.ProjectMemberService;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;

@SpringBootTest
@AutoConfigureMockMvc
@NoArgsConstructor
@Transactional
@TestPropertySource(locations = "classpath:application-data.properties")
class ProjectMemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectMemberService projectMemberService;

    @Test
    @DisplayName("프로젝트 멤버 추가")
    void addProjectMember() throws Exception {
        // Given
        Long projectId = 1L;
        String memberId = "member123";
        CreateProjectMemberRequestDTO request = new CreateProjectMemberRequestDTO();
        request.setMemberId(memberId);

        ProjectMemberResponseDto.ProjectMemberDTO projectMemberDTO = new ProjectMemberResponseDto.ProjectMemberDTO(1L,
                projectId, "member123",
                Collections.singleton(memberId));

        ApiResponse<ProjectMemberResponseDto.ProjectMemberDTO> response = ApiResponse.onSuccess(
                SuccessStatus.MEMBER_ADD_OK,
                projectMemberDTO);

        // Stubbing
        when(projectMemberService.addMemberToProject(anyLong(), anyString())).thenReturn(response);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/projects/{projectId}/members", projectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").exists())
                .andExpect(jsonPath("$.result.projectId").value(projectId))
                .andExpect(jsonPath("$.result.name").value("member123"))
                .andExpect(jsonPath("$.result.memberIds[0]").value(memberId));
    }

    @Test
    @DisplayName("프로젝트 개발자 목록 조회")
    void getProjectDevs() throws Exception {
        // Given
        Long projectId = 1L;
        ProjectDevDto devDto = ProjectDevDto.builder()
                .devList(Arrays.asList("dev1", "dev2", "dev3"))
                .build();

        ApiResponse<ProjectDevDto> response = ApiResponse.onSuccess(SuccessStatus.PROJECT_OK, devDto);

        when(projectMemberService.getProjectDevList(projectId)).thenReturn(Arrays.asList("dev1", "dev2", "dev3"));

        // When & Then
        mockMvc.perform(get("/projects/{projectId}/members/dev", projectId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.devList[0]").value("dev1"))
                .andExpect(jsonPath("$.result.devList[1]").value("dev2"))
                .andExpect(jsonPath("$.result.devList[2]").value("dev3"));
    }
}
