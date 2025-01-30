
package com.messenger_backend.security;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.messenger_backend.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    
    private final UserService userService;
    

    public JwtFilter(JwtUtil jwtUtil,UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }
        
        System.out.println("Extracted Token: " + token);
        System.out.println("Extracted Username: " + username);

        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	var userDetails =userService.loadUserByUsername(username);

        	 if (jwtUtil.validateToken(token, userDetails)) {
        	        	var authentication= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        	 }
        }
        else {
            System.out.println("It is a login");
        }

        filterChain.doFilter(request, response);
    }
}
