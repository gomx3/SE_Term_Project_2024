package SE_team.IssueManager.service;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.dto.MemberRequestDto;
import SE_team.IssueManager.payload.code.status.ErrorStatus;
import SE_team.IssueManager.payload.exception.handler.MemberHandler;
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
        if(request.getMemberId().isEmpty() || request.getPw().isEmpty() || request.getRole()==null)
            throw new MemberHandler(ErrorStatus.MEMBER_BAD_REQUEST);
        Member member=Member.builder()
                        .pw(request.getPw())
                        .memberId(request.getMemberId())
                        .role(request.getRole()).build();

        validateDuplicateMember(member);
        Member savedMember=memberRepository.save(member);
        return savedMember;
    }

    public void validateDuplicateMember(Member member) {    //아이디 중복 확인
        memberRepository.findByMemberId(member.getMemberId())
                .ifPresent(mem -> {
                    throw new MemberHandler(ErrorStatus.MEMBER_ID_EXISTS);
                });
    }

    public List<Member> findMembers(){      //전체 회원 조회
        return memberRepository.findAll();
    }
    public Optional<Member> findMemberById(long id) { //아이디로 회원 조회
        return memberRepository.findById(id);
    }
}
