package SE_team.IssueManager.service;

import SE_team.IssueManager.domain.Member;
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
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setId("spring");
        member.setPw("1234");

        //When
        String saveId=memberService.signUp(member);

        //Then
        Member findMem=memberService.findMemberById(saveId).get();
        assertEquals(member.getId(),findMem.getId());
        System.out.println(member.getId()+ findMem.getId());
    }

    @Test
    void 중복아이디_예외() throws Exception{
        //Given
        Member member1 = new Member();
        member1.setId("spring");
        member1.setPw("1234");

        Member member2 = new Member();
        member2.setId("spring");
        member2.setPw("12345");

        //When
        memberService.signUp(member1);
        IllegalStateException e=assertThrows(IllegalStateException.class,
                ()->memberService.signUp(member2));     //중복 아이디로 가입할 경우 예외 발생
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); //예외메시지가 올바른지?

    }

}