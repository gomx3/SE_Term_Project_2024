package SE_team.IssueManager.domain;

import SE_team.IssueManager.domain.enums.Category;
import SE_team.IssueManager.domain.enums.Priority;
import SE_team.IssueManager.domain.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;


import java.time.LocalDateTime;

@Entity
@Setter
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

    @Setter
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

//    @ManyToOne(fetch =FetchType.LAZY)
//    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name="category")
    @Builder.Default
    private Category category=Category.OTHERS;


}
