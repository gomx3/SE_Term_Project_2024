package SE_team.IssueManager.service;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memRepo;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memRepo=memberRepository;
    }

    public String signUp(Member member) {     //회원가입 서비스
        validateDuplicateMember(member);
        memRepo.save(member);
        return member.getId();
    }

    public void validateDuplicateMember(Member member) {    //아이디 중복 확인
        memRepo.findById(member.getId())
                .ifPresent(mem -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<Member> findMembers(){      //전체 회원 조회
        return memRepo.findAll();
    }
    public Optional<Member> findMemberById(String id) { //아이디로 회원 조회
        return memRepo.findById(id);
    }
}
