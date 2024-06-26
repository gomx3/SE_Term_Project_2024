package SE_team.IssueManager.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.web.dto.MemberRequestDto;
import SE_team.IssueManager.web.dto.MemberResponseDto;
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
        String encryptedPassword = passwordEncoder.encode(request.getPw());
        //빈 문자열인지 검사
        if(Objects.equals(request.getMemberId(), "") ||request.getMemberId()==null|| Objects.equals(request.getPw(), "") ||request.getPw()==null){
            throw new MemberHandler(ErrorStatus.MEMBER_BAD_REQUEST);
        }
        Member member = Member.builder()
                .pw(encryptedPassword)
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

    public void validateMembersExist(Set<String> memberIds) {
        Set<Member> existingMembers = memberRepository.findByMemberIdIn(memberIds);
        Set<String> existingMemberIds = existingMembers.stream().map(Member::getMemberId).collect(Collectors.toSet());

        for (String memberId : memberIds) {
            if (!existingMemberIds.contains(memberId)) {
                throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
            }
        }
    }

    public Set<Member> findMembersByIds(Set<String> memberIds) {
        return memberRepository.findByMemberIdIn(memberIds);
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
