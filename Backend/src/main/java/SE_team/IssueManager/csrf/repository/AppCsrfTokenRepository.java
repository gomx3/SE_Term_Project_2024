package SE_team.IssueManager.csrf.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SE_team.IssueManager.csrf.entity.AppCsrfToken;

@Repository
public interface AppCsrfTokenRepository extends JpaRepository<AppCsrfToken, String> {

    Optional<AppCsrfToken> findAppCsrfTokenByAppId(String appId);
}