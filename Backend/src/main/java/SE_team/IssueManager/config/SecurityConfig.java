package SE_team.IssueManager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-colsole/**")))
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                /* spring security의 로그인 설정 담당 */
                .formLogin((formLogin) -> formLogin
                        /* 로그인 페이지의 url은 "/user/login" */
                        .loginPage("/user/login")
                        /* 로그인 성공시 이동할 페이지는 루트 */
                        .defaultSuccessUrl("/"))
                /* spring security의 로그아웃 설정 담당 */
                .logout((logout) -> logout
                        /* 로그아웃 페이지의 url은 "/user/logout" */
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        /* 로그아웃 성공시 이동할 페이지는 루트 */
                        .logoutSuccessUrl("/")
                        /* 로그아웃 시 생성된 사용자 세션 삭제 */
                        .invalidateHttpSession(true));
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}