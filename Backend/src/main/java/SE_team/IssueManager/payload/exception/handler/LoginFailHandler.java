package SE_team.IssueManager.payload.exception.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String message = getMessage(exception);
        String redirectUrl = "/account/login?hasMessage=true&message=" + message;
        setDefaultFailureUrl(redirectUrl);
        super.onAuthenticationFailure(request, response, exception);
    }

    private static String getMessage(AuthenticationException exception) throws UnsupportedEncodingException {
        String message = exception.getMessage();
        String encodeMessage = URLEncoder.encode(message, "UTF-8");
        return encodeMessage;
    }
}