package SE_team.IssueManager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.repository.MemberRepository;

class MemberDetailsServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberDetailsServiceImpl memberDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("사용자 정보 로드 - 성공")
    void loadUserByUsername_Success() {
        // Given
        String memberId = "user123";
        Member member = new Member();
        member.setMemberId(memberId);

        when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.of(member));

        // When
        UserDetails userDetails = memberDetailsService.loadUserByUsername(memberId);

        // Then
        assertEquals(memberId, userDetails.getUsername());
    }

    @Test
    @DisplayName("사용자 정보 로드 - 실패: 사용자를 찾을 수 없음")
    void loadUserByUsername_UserNotFound() {
        // Given
        String memberId = "nonexistent_user";

        when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UsernameNotFoundException.class, () -> memberDetailsService.loadUserByUsername(memberId));
    }
}