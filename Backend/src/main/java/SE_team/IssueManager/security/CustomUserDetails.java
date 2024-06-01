package SE_team.IssueManager.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import SE_team.IssueManager.domain.Member;

public class CustomUserDetails implements UserDetails {

    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()));
    }

    @Override
    public String getPassword() {
        return member.getPw();
    }

    @Override
    public String getUsername() {
        return member.getMemberId();
    }

    @Override
    public boolean isEnabled() {
        // 사용자 계정이 활성화되어 있는지 여부를 반환
        // 예: return member.isActive();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 자격 증명이 만료되었는지 여부를 반환
        // 예: return !member.isCredentialsExpired();
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정이 만료되었는지 여부를 반환
        // 예: return !member.isAccountExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정이 잠겨 있는지 여부를 반환
        // 예: return !member.isAccountLocked();
        return true;
    }

}
