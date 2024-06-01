package SE_team.IssueManager.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SE_team.IssueManager.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    public Optional<Member> findByMemberId(String memberId);

    Set<Member> findByMemberIdIn(Set<String> memberIds);
}
