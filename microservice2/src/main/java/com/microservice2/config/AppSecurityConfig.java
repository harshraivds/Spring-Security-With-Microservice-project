package com.microservice2.config;


import com.microservice2.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Autowired
    private JwtFilter filter;

    String[] publicEndpoints = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/actuator/**",
            "/eureka/**"
    };

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception{

            http
                    .csrf(csrf -> csrf.disable()) // Disable CSRF for REST APIs
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(publicEndpoints).permitAll()
                            .requestMatchers("/api/v1/call/message").hasAnyRole("ADMIN", "USER")
                            .anyRequest().authenticated()
                    )
                    .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }

}
