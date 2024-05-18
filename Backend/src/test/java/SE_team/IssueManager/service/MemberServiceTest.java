package SE_team.IssueManager.service;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.enums.Role;
import SE_team.IssueManager.dto.MemberRequestDto;
import SE_team.IssueManager.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired  MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() throws Exception{

        //Given
        MemberRequestDto.SignUpReqDTO member = MemberRequestDto.SignUpReqDTO.builder()
            .email("spring")
            .pw("1234")
            .role(Role.ADMIN).build();

        //When
        Member saveMem=memberService.signUp(member);

//        System.out.println("member:"+saveMem.getId());

//        Optional<Member> findMember=memberService.findMemberById(saveMem.getId());
//        if (findMember.isPresent()) {
//            System.out.println(findMember.get().getEmail());
//        }
//        else{
//            System.out.println("cannot find member");
//        }

        //Then
        Member findMember=memberService.findMemberById(saveMem.getId()).get();
        assertEquals(saveMem.getId(),findMember.getId());
    }

    @Test
    void 중복이메일_확인(){
        //Given
        MemberRequestDto.SignUpReqDTO member1 = MemberRequestDto.SignUpReqDTO.builder()
                .email("spring")
                .pw("1234")
                .role(Role.ADMIN).build();

        MemberRequestDto.SignUpReqDTO member2 = MemberRequestDto.SignUpReqDTO.builder()
                .email("spring")
                .pw("1234")
                .role(Role.ADMIN).build();

        //When
        memberService.signUp(member1);
        IllegalStateException e=assertThrows(IllegalStateException.class,
                ()->memberService.signUp(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }
}