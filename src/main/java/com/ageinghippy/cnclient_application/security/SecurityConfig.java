package com.ageinghippy.cnclient_application.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/webjars/**", "/css/**", "/images/**", "/js/**").permitAll()
                        .requestMatchers("/", "/homepage", "/register", "/login").permitAll()
                        .requestMatchers("/items").authenticated()
                        .anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults());
                .formLogin(login -> login.loginPage("/login").permitAll() //ToDo - should this only be for anonymous?
                        .defaultSuccessUrl("/homepage", true))
                .logout(logout -> logout.logoutUrl("/logout"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
