package SE_team.IssueManager.domain;

import SE_team.IssueManager.domain.enums.Role;
import jakarta.persistence.*;

@Entity
public class Member {

    @Id
    private String id;

    @Column(name="pw")
    private String pw;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role;



    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
