package SE_team.IssueManager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import SE_team.IssueManager.web.dto.ProjectMemberReqeustDto;
import SE_team.IssueManager.web.dto.ProjectMemberResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.Project;
import SE_team.IssueManager.domain.ProjectMember;
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
        private MockMvc mockMvc;

        @Mock
        private ProjectRepository projectRepository;

        @Mock
        private MemberRepository memberRepository;

        @Mock
        private ProjectMemberRepository projectMemberRepository;

        @InjectMocks
        private ProjectMemberService projectMemberService;

        ObjectMapper objectMapper = new ObjectMapper();

        @Test
        @DisplayName("프로젝트에 멤버 추가 - 성공")
        void addMemberToProject_Success() throws Exception {
                // Given
                Long projectId = 1L;
                String memberId = "user123";

                Project project = Project.builder()
                                .id(projectId)
                                .name("Test Project")
                                .build();

                Member member = Member.builder()
                                .id(1L)
                                .memberId(memberId)
                                .pw("password")
                                .role(Role.DEV)
                                .build();

                ProjectMember savedProjectMember = ProjectMember.builder()
                                .id(1L)
                                .project(project)
                                .member(member)
                                .build();

                when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
                when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.of(member));
                when(projectMemberRepository.save(any(ProjectMember.class))).thenReturn(savedProjectMember);

                // When
                ApiResponse<ProjectMemberDTO> response = projectMemberService.addMemberToProject(projectId, memberId);

                System.out.println(response.getResult().getProjectId());
                assertEquals(projectId,response.getResult().getProjectId());

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
