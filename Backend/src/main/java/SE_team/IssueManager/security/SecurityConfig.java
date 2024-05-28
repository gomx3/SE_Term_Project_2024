package SE_team.IssueManager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import SE_team.IssueManager.csrf.CsrfCookieFilter;
import SE_team.IssueManager.payload.exception.handler.LoginFailHandler;
import SE_team.IssueManager.payload.exception.handler.LoginSuccessHandler;
import SE_team.IssueManager.service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private final MemberService memberService;

        @Autowired // 의존성 주입
        public SecurityConfig(MemberService memberService) {
                this.memberService = memberService;
        }

        private static final int ONE_MONTH = 2678400;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
                requestHandler.setCsrfRequestAttributeName("_csrf");
                return http
                                .cors().and()
                                .csrf(csrf -> csrf
                                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                                .authorizeHttpRequests(request -> request
                                                .requestMatchers("/cart").hasRole("MEMBER")
                                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                                .permitAll()
                                                .anyRequest().permitAll())
                                .formLogin(customizer -> customizer
                                                .loginPage("/account/login")
                                                .loginProcessingUrl("/account/login")
                                                .usernameParameter("email")
                                                .successHandler(new LoginSuccessHandler("/"))
                                                .failureHandler(new LoginFailHandler())
                                                .permitAll())
                                .rememberMe(customizer -> customizer
                                                .rememberMeParameter("remember-me")
                                                .tokenValiditySeconds(ONE_MONTH)
                                                .userDetailsService(memberService) // 변경된 부분
                                                .authenticationSuccessHandler(new LoginSuccessHandler()))
                                .logout(customizer -> customizer
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/")
                                                .deleteCookies("remember-me")
                                                .permitAll())
                                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                                .httpBasic(Customizer.withDefaults())
                                .build();
        }

        @Bean
        public AuthenticationManager authenticationManager() throws Exception {
                return authentication -> null; // 비활성화된 인증 매니저
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
