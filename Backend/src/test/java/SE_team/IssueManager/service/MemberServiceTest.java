package SE_team.IssueManager.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.enums.Role;
import SE_team.IssueManager.web.dto.MemberRequestDto;
import SE_team.IssueManager.payload.exception.handler.MemberHandler;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원가입 테스트")
    void signUp() throws Exception {

        // Given
        MemberRequestDto.SignUpRequestDTO member = MemberRequestDto.SignUpRequestDTO.builder()
                .memberId("spring")
                .pw("1234")
                .role(Role.ADMIN).build();

        // When
        Member saveMem = memberService.signUp(member);

        // Then
        assertEquals("spring", saveMem.getMemberId());
    }

    @Test
    @DisplayName("중복 memberId 테스트")
    void sign_up_duplicated_memberId() throws Exception {
        // Given
        MemberRequestDto.SignUpRequestDTO member1 = MemberRequestDto.SignUpRequestDTO.builder()
                .memberId("spring")
                .pw("1234")
                .role(Role.ADMIN).build();

        MemberRequestDto.SignUpRequestDTO member2 = MemberRequestDto.SignUpRequestDTO.builder()
                .memberId("spring")
                .pw("1234")
                .role(Role.ADMIN).build();

        // When
        memberService.signUp(member1);

        // Then
        Assertions.assertThrows(MemberHandler.class, () -> memberService.signUp(member2));
    }

    @Test
    @DisplayName("memberId 빈칸 테스트")
    void sign_up_member_id_empty() throws Exception {
        // Given
        MemberRequestDto.SignUpRequestDTO member1 = MemberRequestDto.SignUpRequestDTO.builder()
                .memberId("") // memberId가 빈칸
                .pw("1234")
                .role(Role.ADMIN).build();
        // Then
        Assertions.assertThrows(MemberHandler.class, () -> memberService.signUp(member1));
    }
}
