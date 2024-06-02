package SE_team.IssueManager.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.project.dto.FindMyProjectResponseDto.FindMyProjectRespDTO;
import SE_team.IssueManager.project.dto.FindMyProjectResponseDto.FindMyProjectRespDTO.ProjectInfo;
import SE_team.IssueManager.project.service.FindMyProjectService;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;

@AutoConfigureMockMvc
@SpringBootTest
@NoArgsConstructor
@Transactional
@TestPropertySource(locations = "classpath:application-data.properties")
class FindMyProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindMyProjectService findMyProjectService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("내 프로젝트 조회")
    void findMyProjects() throws Exception {
        // Given
        String memberId = "user123";
        List<ProjectInfo> projectInfoList = Arrays.asList(
                new ProjectInfo(1L, "Project A"),
                new ProjectInfo(2L, "Project B"),
                new ProjectInfo(3L, "Project C"));
        FindMyProjectRespDTO projectRespDTO = new FindMyProjectRespDTO(memberId, projectInfoList);

        ApiResponse<FindMyProjectRespDTO> response = ApiResponse.onSuccess(SuccessStatus.PROJECT_FIND_OK,
                projectRespDTO);

        when(findMyProjectService.findMyProjects(memberId)).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/projects/{memberId}/check", memberId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.memberId").value(memberId))
                .andExpect(jsonPath("$.result.projectIds[0].projectId").value(1))
                .andExpect(jsonPath("$.result.projectIds[0].projectName").value("Project A"))
                .andExpect(jsonPath("$.result.projectIds[1].projectId").value(2))
                .andExpect(jsonPath("$.result.projectIds[1].projectName").value("Project B"))
                .andExpect(jsonPath("$.result.projectIds[2].projectId").value(3))
                .andExpect(jsonPath("$.result.projectIds[2].projectName").value("Project C"));
    }
}
