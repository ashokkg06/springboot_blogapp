package com.example.blogapp.blogapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.blogapp.blogapp.users.UsersService;
@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
    
    private JWTAuthenticationFilter jwtAuthenticationFilter;
    private JWTService jwtService;
    private UsersService usersService;

    public AppSecurityConfig(JWTService jwtService, UsersService usersService) {
        this.jwtService = jwtService;
        this.usersService = usersService;
        this.jwtAuthenticationFilter = new JWTAuthenticationFilter(
            new JWTAuthenticationManager(jwtService, usersService)
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/users"), 
                    AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/h2-console/**"),
                    AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/articles"),
                    AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/articles/*"),
                    AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/articles"),
                    AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/users/**"), 
                    AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/h2-console/**"))
                    .permitAll()
                .anyRequest().authenticated()
            )
            // .formLogin(formLogin -> formLogin
            //     .loginPage("/users/login")
            //     .permitAll()
            // ) 
            // .headers().frameOptions().sameOrigin()
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions
                    .sameOrigin()))
            .rememberMe(Customizer.withDefaults());
            
        http.addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);
        return http.build();
    }
}
