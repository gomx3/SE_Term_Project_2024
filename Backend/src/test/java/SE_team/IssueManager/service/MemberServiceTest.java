package SE_team.IssueManager.service;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.enums.Role;
import SE_team.IssueManager.dto.MemberRequestDto;
import SE_team.IssueManager.payload.code.status.ErrorStatus;
import SE_team.IssueManager.payload.exception.GeneralException;
import SE_team.IssueManager.payload.exception.handler.MemberHandler;
import SE_team.IssueManager.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-data.properties")
class MemberServiceTest{
    @Autowired  MemberService memberService;

    @Test
    @DisplayName("회원가입 테스트")
    void signUp() throws Exception{

        //Given
        MemberRequestDto.SignUpRequestDTO member = MemberRequestDto.SignUpRequestDTO.builder()
            .memberId("spring")
            .pw("1234")
            .role(Role.ADMIN).build();

        //When
        Member saveMem=memberService.signUp(member);

        //Then
        assertEquals("spring",saveMem.getMemberId());
    }

    @Test
    @DisplayName("중복 memberId 테스트")
    void sign_up_duplicated_memberId() throws Exception{
        //Given
        MemberRequestDto.SignUpRequestDTO member1 = MemberRequestDto.SignUpRequestDTO.builder()
                .memberId("spring")
                .pw("1234")
                .role(Role.ADMIN).build();

        MemberRequestDto.SignUpRequestDTO member2 = MemberRequestDto.SignUpRequestDTO.builder()
                .memberId("spring")
                .pw("1234")
                .role(Role.ADMIN).build();

        //When
        memberService.signUp(member1);

        //Then
        Assertions.assertThrows(MemberHandler.class,()->memberService.signUp(member2));
    }

    @Test
    @DisplayName("memberId 빈칸 테스트")
    void sign_up_member_id_empty() throws Exception{
        //Given
        MemberRequestDto.SignUpRequestDTO member1 = MemberRequestDto.SignUpRequestDTO.builder()
                .memberId("")   //memberId가 빈칸
                .pw("1234")
                .role(Role.ADMIN).build();
        //Then
        Assertions.assertThrows(MemberHandler.class,()->memberService.signUp(member1));
    }
}