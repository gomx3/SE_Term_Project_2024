package SE_team.IssueManager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.fasterxml.jackson.databind.ObjectMapper;

import SE_team.IssueManager.filter.JsonUsernamePasswordAuthenticationFilter;
import SE_team.IssueManager.service.MemberDetailsServiceImpl;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final MemberDetailsServiceImpl memberDetailsService;
        private final ObjectMapper objectMapper;

        // 스프링 시큐리티 기능 비활성화 (H2 DB 접근을 위해)
        // @Bean
        // public WebSecurityCustomizer configure() {
        // return (web -> web.ignoring()
        // .requestMatchers(toH2Console())
        // .requestMatchers("/h2-console/**")
        // );
        // }

        // 특정 HTTP 요청에 대한 웹 기반 보안 구성
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.csrf(AbstractHttpConfigurer::disable)
                                .httpBasic(AbstractHttpConfigurer::disable)
                                .formLogin(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests((authorize) -> authorize
                                                .anyRequest().permitAll())
                                // 폼 로그인은 현재 사용하지 않음
                                // .formLogin(formLogin -> formLogin
                                // .loginPage("/login")
                                // .defaultSuccessUrl("/home"))
                                .logout((logout) -> logout
                                                .logoutSuccessUrl("/login")
                                                .invalidateHttpSession(true))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                return http.build();
        }

        // 인증 관리자 관련 설정
        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
                DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

                daoAuthenticationProvider.setUserDetailsService(memberDetailsService);
                daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

                return daoAuthenticationProvider;
        }

        @Bean
        public static PasswordEncoder passwordEncoder() {
                return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager() throws Exception {// AuthenticationManager 등록
                DaoAuthenticationProvider provider = daoAuthenticationProvider();// DaoAuthenticationProvider 사용
                provider.setPasswordEncoder(passwordEncoder());// PasswordEncoder로는
                                                               // PasswordEncoderFactories.createDelegatingPasswordEncoder()
                                                               // 사용
                return new ProviderManager(provider);
        }

        @Bean
        public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter() throws Exception {
                JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter = new JsonUsernamePasswordAuthenticationFilter(
                                objectMapper);
                jsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
                return jsonUsernamePasswordLoginFilter;
        }

}
