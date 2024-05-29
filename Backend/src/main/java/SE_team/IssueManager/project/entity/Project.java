package SE_team.IssueManager.project.entity;

import java.util.HashSet;
import java.util.Set;

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
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToMany
    @JoinTable(name = "project_members", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "member_id"))

    // private List<Member> members;

    private Set<Member> members = new HashSet<>();

    @Builder
    public Project(String name, String description, Set<Member> members) {
        this.name = name;
        this.description = description;
        this.members = new HashSet<>(members);
    }

    public void addMembers(Set<Member> members) {
        this.members.addAll(members);
    }

}
