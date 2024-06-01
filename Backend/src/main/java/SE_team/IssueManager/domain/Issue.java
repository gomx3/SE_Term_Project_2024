package SE_team.IssueManager.domain;

import SE_team.IssueManager.domain.enums.Category;
import SE_team.IssueManager.domain.enums.Priority;
import SE_team.IssueManager.domain.enums.Status;
import SE_team.IssueManager.project.entity.Project;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Issue extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @ManyToOne(fetch =FetchType.LAZY)
    private Member reporter;

    @ManyToOne(fetch =FetchType.LAZY)
    private Member fixer;

    @ManyToOne(fetch =FetchType.LAZY)
    private Member assignee;

    @Enumerated(EnumType.STRING)
    @Column(name="priority")
    @Builder.Default
    private Priority priority=Priority.MAJOR;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    @Builder.Default
    private Status status=Status.NEW;

    @ManyToOne(fetch =FetchType.LAZY)
    private Project project;
//    @Column(name="projectId")
//    private Long projectId;

    @OneToMany(mappedBy = "issue",cascade = CascadeType.ALL)
    private List<Comment> commentList=new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name="category")
    @Builder.Default
    private Category category=Category.OTHERS;


    public void updateAssignee(Member assignee) {
        this.assignee = assignee;
    }

    public void updateFixer(Member fixer) {
        this.fixer = fixer;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}
