package SE_team.IssueManager.csrf.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;

import SE_team.IssueManager.csrf.entity.AppCsrfToken;
import SE_team.IssueManager.csrf.repository.AppCsrfTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public record CsrfTokenRepositoryImpl(

        AppCsrfTokenRepository appCsrfTokenRepository

) implements CsrfTokenRepository {

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {

        /* generate token and return an instance of csrf token */
        String token = UUID.randomUUID().toString();

        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {

        response.setHeader(token.getHeaderName(), token.getToken());

        /* get the token from the request */
        String requestAppId = request.getHeader("X-APP-ID");

        /* check if token exists */
        Optional<AppCsrfToken> optAppCsrfToken = appCsrfTokenRepository.findAppCsrfTokenByAppId(requestAppId);

        if (optAppCsrfToken.isPresent()) {

            /* get the token and change the value */
            AppCsrfToken appCsrfToken = optAppCsrfToken.get();
            appCsrfToken.setToken(token.getToken());
            return;
        }

        /* create a new record, set properties and return */
        AppCsrfToken appCsrfToken = new AppCsrfToken();
        appCsrfToken.setAppId(requestAppId);
        appCsrfToken.setToken(token.getToken());
        appCsrfToken.setExpired(false);

        appCsrfTokenRepository.save(appCsrfToken);
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {

        /* get the app ticketNumber */
        String appId = request.getHeader("X-APP_ID");

        /* check if token exists */
        Optional<AppCsrfToken> optSavedToken = appCsrfTokenRepository.findAppCsrfTokenByAppId(appId);

        if (optSavedToken.isPresent()) {

            AppCsrfToken savedToken = optSavedToken.get();
            return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", savedToken.getToken());
        }

        return null;
    }
}