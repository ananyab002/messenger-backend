package com.messenger_backend.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.messenger_backend.service.UserService;

@Configuration
public class SecurityConfig {

	 private final JwtFilter jwtFilter;
	    private final UserService userService;
	    private final PasswordEncoder passwordEncoder;

	    public SecurityConfig(JwtFilter jwtFilter, UserService userService, PasswordEncoder passwordEncoder) {
	        this.jwtFilter = jwtFilter;
	        this.userService = userService;
	        this.passwordEncoder = passwordEncoder;
	    }
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        // Enable CORS globally
	        http.csrf(csrf -> csrf.disable())
	            .cors() // Enable CORS globally
	            .and()
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/auth/**").permitAll() // Allow auth-related routes to be public
	                .requestMatchers("/users/search").authenticated() 
	                .anyRequest().authenticated() // Any other request should be authenticated
	            )
	            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before authentication
	        return http.build();
	    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");  // Allow frontend origin
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}