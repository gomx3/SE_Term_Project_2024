package SE_team.IssueManager.domain;

import SE_team.IssueManager.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="memberId")
    private String memberId;

    @Column(name="pw")
    private String pw;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    @Builder.Default
    private Role role=Role.DEV;


}
