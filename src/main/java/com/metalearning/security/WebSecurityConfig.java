package com.metalearning.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class WebSecurityConfig {

    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    private static final String[] WHITE_LIST = { "/", "/login", "/signup", "find/**","main/**"};

    // 스태틱 리소스는 인증 없이 접근할 수 있도록 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/static/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                react cors 설정
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // CSRF 설정
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())  // CSRF 토큰을 쿠키로 처리
                )
                // 요청 URL에 대한 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITE_LIST).permitAll() // 화이트리스트는 모두 허용
                        .requestMatchers("/admin/**", "/api/admin/**", "view/admin/**").hasRole("ADMIN") // ADMIN 역할만 허용
                        .requestMatchers("/users/**", "manager/**", "/api/manager/**").hasAnyRole("ADMIN", "MANAGER") // ADMIN 또는 MANAGER 역할만 허용
                        .requestMatchers("/INSTRUCTOR/**").hasRole("INSTRUCTOR") // INSTRUCTOR 역할만 허용
                        .requestMatchers("/student/**").hasRole("STUDENT") // STUDENT 역할만 허용
                        .requestMatchers("/KDT/**", "/course/**").hasAnyRole("ADMIN", "INSTRUCTOR", "MANAGER") // KDT와 course 경로는 ADMIN, INSTRUCTOR, MANAGER 역할만 허용
                        .anyRequest().authenticated() // 나머지 요청은 인증된 사용자만 접근 가능
                )
                // 로그인 설정
                .formLogin(form -> form
                        .loginPage("/login")  // 로그인 페이지 경로
                        .successHandler(customAuthenticationSuccessHandler) // 로그인 성공 후 처리
                )
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutSuccessHandler(customLogoutSuccessHandler) // 로그아웃 후 처리
                        .invalidateHttpSession(true)  // 세션 무효화
                        .clearAuthentication(true)   // 인증 정보 제거
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                )
                // 세션 관리 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // 세션 생성 정책 설정
                        .maximumSessions(1)  // 최대 세션 수 1로 제한
                        .maxSessionsPreventsLogin(true)  // 기존 세션이 있을 경우 새로운 로그인 차단
                        .expiredUrl("/login")  // 세션 만료 시 리디렉션할 URL 설정
                )
                // 예외 처리 설정
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/accessDenied")  // 권한이 없을 경우 접근 페이지 설정
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/login");  // 인증되지 않은 경우 리디렉션
                        })
                );

        return http.build();
    }

    // BCryptPasswordEncoder Bean 설정
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();  // 비밀번호 암호화에 사용할 BCryptPasswordEncoder Bean 설정
    }

    // AuthenticationManager Bean 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService);  // UserDetailsService 설정
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());  // 비밀번호 인코더 설정

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authProvider)  // AuthenticationProvider 설정
                .build();  // AuthenticationManager 반환
    }

//    react 병합전 cors 문제 해결 코드
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173"); // React 클라이언트 URL
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 쿠키 기반 인증 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
