package SE_team.IssueManager.csrf.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "app_csrf_token")
@Data
public class AppCsrfToken {

    @Id
    @Column(nullable = false, name = "app_id", unique = true)
    private String appId;

    @Column(nullable = false, name = "token")
    private String token;

    @Column(name = "expired")
    private boolean expired;

}