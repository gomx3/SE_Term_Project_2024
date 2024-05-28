package SE_team.IssueManager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.dto.MemberRequestDto;
import SE_team.IssueManager.dto.MemberResponseDto;
import SE_team.IssueManager.payload.code.status.ErrorStatus;
import SE_team.IssueManager.payload.exception.handler.MemberHandler;
import SE_team.IssueManager.repository.MemberRepository;
import SE_team.IssueManager.security.CustomUserDetails;

@Service
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new CustomUserDetails(member);
    }

    public Member signUp(MemberRequestDto.SignUpRequestDTO request) { // 회원가입 서비스
        Member member = Member.builder()
                .pw(passwordEncoder.encode(request.getPw())) // 비밀번호 암호화
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

    @Transactional
    public void save(MemberResponseDto.MemberSaveDTO memberSaveDto) {
        Member member = Member.builder()
                .memberId(memberSaveDto.getMemberId())
                .pw(passwordEncoder.encode(memberSaveDto.getPassword()))
                .role(memberSaveDto.getRole())
                .build();
        memberRepository.save(member);
    }
}
