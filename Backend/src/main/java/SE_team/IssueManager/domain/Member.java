package SE_team.IssueManager.domain;

import SE_team.IssueManager.domain.enums.Role;
import SE_team.IssueManager.project.entity.ProjectMember;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="memberId",nullable=false)
    private String memberId;

    @Column(name="pw",nullable = false)
    private String pw;

    @Enumerated(EnumType.STRING)
    @Column(name="role",nullable = false)
    @Builder.Default
    private Role role=Role.DEV;

    @OneToMany(mappedBy = "reporter",cascade = CascadeType.ALL)
    private List<Issue> reportedIssueList=new ArrayList<>();

    @OneToMany(mappedBy = "fixer",cascade = CascadeType.ALL)
    private List<Issue> fixedIssueList=new ArrayList<>();

    @OneToMany(mappedBy = "assignee",cascade = CascadeType.ALL)
    private List<Issue> assignedIssueList=new ArrayList<>();

    @OneToMany(mappedBy = "writer",cascade = CascadeType.ALL)
    private List<Comment> commentList=new ArrayList<>();

    @OneToMany(mappedBy="member",cascade = CascadeType.ALL)
    private List<ProjectMember> projectMemberList=new ArrayList<>();

}
