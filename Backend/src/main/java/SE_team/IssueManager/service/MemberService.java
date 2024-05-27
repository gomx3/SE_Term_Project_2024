package SE_team.IssueManager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.dto.MemberRequestDto;
import SE_team.IssueManager.payload.code.status.ErrorStatus;
import SE_team.IssueManager.payload.exception.handler.MemberHandler;
import SE_team.IssueManager.repository.MemberRepository;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member signUp(MemberRequestDto.SignUpRequestDTO request) { // 회원가입 서비스
        Member member = Member.builder()
                .pw(request.getPw())
                .memberId(request.getMemberId())
                .role(request.getRole()).build();

        validateDuplicateMember(member);
        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    public void validateDuplicateMember(Member member) { // 아이디 중복 확인
        memberRepository.findByMemberId(member.getMemberId())
                .ifPresent(mem -> {
                    throw new MemberHandler(ErrorStatus.MEMBER_ID_EXISTS);
                });
    }

    public List<Member> findMembers() { // 전체 회원 조회
        return memberRepository.findAll();
    }

    public Optional<Member> findMemberById(long id) { // 아이디로 회원 조회
        return memberRepository.findById(id);
    }

    public Member login(String memberId, String pw) { // 로그인 서비스
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Member member = optionalMember.orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // 비밀번호 확인
        if (!member.getPw().equals(pw)) {
            throw new MemberHandler(ErrorStatus.INVALID_PASSWORD);
        }

        return member;
    }
}
