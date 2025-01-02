package com.joaocarv05.teste_tecnico_picpay.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
public class SecurityConfiguration {

    @Autowired
    FilterCustom filterCustom;
    @Autowired
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorizationManager -> authorizationManager
                        .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/transfer").hasRole("COMMON_USER")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityContext(httpSecuritySecurityContextConfigurer -> httpSecuritySecurityContextConfigurer.requireExplicitSave(true))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(filterCustom, UsernamePasswordAuthenticationFilter.class)
                .authenticationManager(authenticationManager)
                .httpBasic(basic -> basic.authenticationEntryPoint(customAuthenticationEntryPoint))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
