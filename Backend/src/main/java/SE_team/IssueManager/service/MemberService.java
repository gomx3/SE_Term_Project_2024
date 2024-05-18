package SE_team.IssueManager.service;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.dto.MemberRequestDto;
import SE_team.IssueManager.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository =memberRepository;
    }

    public Member signUp(MemberRequestDto.SignUpRequestDTO request) {     //회원가입 서비스
        Member member=Member.builder()
                        .pw(request.getPw())
                        .email(request.getEmail())
                        .role(request.getRole()).build();

        validateDuplicateMember(member);
        Member savedMember=memberRepository.save(member);
        return savedMember;
    }

    public void validateDuplicateMember(Member member) {    //아이디 중복 확인
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(mem -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<Member> findMembers(){      //전체 회원 조회
        return memberRepository.findAll();
    }
    public Optional<Member> findMemberById(long id) { //아이디로 회원 조회
        return memberRepository.findById(id);
    }
}
