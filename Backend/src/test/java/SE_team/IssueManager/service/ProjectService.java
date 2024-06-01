package SE_team.IssueManager.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.status.SuccessStatus;
import SE_team.IssueManager.project.dto.ProjectRequestDto.CreateProjectRequestDTO;
import SE_team.IssueManager.project.dto.ProjectResponseDto.ProjectDTO;
import SE_team.IssueManager.project.entity.Project;
import SE_team.IssueManager.project.repository.ProjectMemberRepository;
import SE_team.IssueManager.project.repository.ProjectRepository;
import SE_team.IssueManager.repository.MemberRepository;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-data.properties")
class ProjectService {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    private Member creator;
    private CreateProjectRequestDTO createProjectRequestDTO;

    @BeforeEach
    void setUp() {
        // 테스트용 멤버 생성
        creator = Member.builder()
                .memberId("creator")
                .build();
        memberRepository.save(creator);

        // 프로젝트 생성 요청 DTO 초기화
        createProjectRequestDTO = CreateProjectRequestDTO.builder()
                .name("Test Project")
                .creatorId(creator.getMemberId())
                .build();
    }

    @Test
    @DisplayName("프로젝트 생성 테스트")
    void createProject() {
        // When
        ApiResponse<ProjectDTO> response = projectService.createProject(createProjectRequestDTO);

        // Then
        assertNotNull(response);
        assertEquals(SuccessStatus.PROJECT_OK, response.getStatus());
        assertNotNull(response.getData());

        ProjectDTO projectDTO = response.getData();
        assertEquals("Test Project", projectDTO.getName());

        Optional<Project> savedProject = projectRepository.findById(projectDTO.getId());
        assertTrue(savedProject.isPresent());

        Optional<Member> savedMember = memberRepository.findByMemberId(creator.getMemberId());
        assertTrue(savedMember.isPresent());

        assertNotNull(projectMemberRepository.findByProjectAndMember(savedProject.get(), savedMember.get()));
    }

    private ApiResponse<ProjectDTO> createProject(CreateProjectRequestDTO createProjectRequestDTO2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createProject'");
    }
}
