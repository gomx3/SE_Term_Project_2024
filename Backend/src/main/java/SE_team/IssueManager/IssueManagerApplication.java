package SE_team.IssueManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IssueManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssueManagerApplication.class, args);
	}

}
