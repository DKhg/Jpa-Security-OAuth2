package hong.board.security.config;

import hong.board.security.auth.MemberAuthFailureHandler;
import hong.board.security.auth.MemberAuthSuccessHandler;
import hong.board.security.auth.MemberPrincipalDetailService;
import hong.board.security.auth.OAuth2MemberService;
import hong.board.security.provider.MemberAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class MemberSecurityConfig {

    //해당 클래스로 MemberPrincipalDetailService 내부 로직 수행, 인증 처리도 같이 진행
    @Autowired
    MemberAuthenticationProvider memberAuthenticationProvider;

    @Autowired
    MemberPrincipalDetailService memberPrincipalDetailService;

    @Autowired
    OAuth2MemberService oAuth2MemberService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/member/**","/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll() // 해당 경로는 인증없이 접근 가능
                        .requestMatchers("/","/board/**").hasAuthority("USER") // ROLE이 USER인 경우에만 인증 가능 hasAuthority 는 ROLE_ 의 접두사를 빼고 비교
                        .anyRequest().authenticated() // 그 외 요청은 인증을 요구
                )
                .requiresChannel(auth -> auth.anyRequest().requiresInsecure()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/member/sendEmail", "/member/idCheck") // /member/sendEmail 경로는 CSRF 보호를 비활성화
                )
                .formLogin(form -> form
                        .loginPage("/loginPage") // 로그인 페이지 설정
                        .loginProcessingUrl("/login") // 로그인 처리 URL 설정, loginPage 의 form action url 과 일치해야함
                        .defaultSuccessUrl("/", true) // 로그인 성공 후 이동할 페이지
                        .failureHandler(new MemberAuthFailureHandler()) // 로그인 실패 후 처리할 핸들러
                        .permitAll() // 로그인 페이지는 모두 접근 가능
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 처리 URL 설정    form action url 과 일치해야함
                        .logoutSuccessUrl("/loginPage?logout=true") // 로그아웃 성공 후 이동할 페이지
                        .deleteCookies("JSESSIONID") // 로그아웃 후 쿠키 삭제
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("hong") // 인증 토큰 생성시 사용할 키
                        .tokenValiditySeconds(60 * 60 * 24 * 7) // 인증 토큰 유효 시간 (7일)
                        .userDetailsService(memberPrincipalDetailService) // 인증 토큰 생성시 사용할 UserDetailsService
                        .rememberMeParameter("remember-me") // 로그인 페이지에서 사용할 파라미터 이름
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(oAuth2MemberService))           // 로그인 사용자 정보 가져오는 로직
                        .defaultSuccessUrl("/", true) // 로그인 성공 후 이동할 페이지
                        .failureHandler(new MemberAuthFailureHandler())
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(memberAuthenticationProvider)
                .build();
    }

}
