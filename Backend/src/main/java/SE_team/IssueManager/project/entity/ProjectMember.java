package SE_team.IssueManager.project.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import SE_team.IssueManager.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @ManyToMany
    @JoinTable(name = "project_members", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "member_id"))

    private Set<Member> members = new HashSet<>();

    @Builder
    public ProjectMember(Set<Member> members) {
        this.members = new HashSet<>(members);
    }

    public void addMembers(Set<Member> members) {
        this.members.addAll(members);
    }

    public Set<String> getMemberIds() {
        // 멤버들의 ID를 저장할 Set 생성
        return this.members.stream()
                .map(Member::getId)
                .map(Object::toString)
                .collect(Collectors.toSet());
    }
}