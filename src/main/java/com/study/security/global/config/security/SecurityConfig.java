package com.study.security.global.config.security;

import com.study.security.global.jwt.filter.JwtFilter;
import com.study.security.global.jwt.util.JwtUtil;
import com.study.security.global.security.handler.CustomAccessDeniedHandler;
import com.study.security.global.security.handler.CustomAuthenticationEntryPoint;
import com.study.security.global.security.member.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
  private final CustomUserDetailsService customUserDetailsService;
  private final JwtUtil jwtUtil;
  private final CustomAccessDeniedHandler accessDeniedHandler;
  private final CustomAuthenticationEntryPoint authenticationEntryPoint;

  // 화이트 리스트 (블랙 리스트의 반대) 화이트 리스트에 있는 주소값은 모두 통과
  private static final String[] AUTH_WHITELIST = {
      "/api/v1/member/**", "/swagger-ui/**", "/api-docs",
      "/swagger-ui-custom.html", "/v3/api-docs/**", "/api-docs/**",
      "/swagger-ui.html", "/api/v1/auth/**", "/api/v1/member"
  };

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        // 위에는 다 사용안하겠다고 막는 것 (예전에 쓰던 방식들이라서 사용하지 않음)
        .addFilterBefore(new JwtFilter(customUserDetailsService, jwtUtil), UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(c -> c.authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler))
        .authorizeHttpRequests(c -> c.requestMatchers(AUTH_WHITELIST).permitAll()
        // 어떤 것을 매칭 시킬 것인지(AUTH_WHITELIST), 어떻게? 모두 제한 해제 (permitAll)
            .anyRequest().permitAll())
            // 나머지도 일단은 제한 해제해둠
        .build();

  }
}
