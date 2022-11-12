package com.example.project.security;

import com.example.project.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer")) {
            String token = authorization.substring(7);
            if (jwtProvider.validateToken(token)) {
                if (jwtProvider.isExpired(token)) {
                    String username = jwtProvider.getUsernameFromToken(token);
                    UserDetails userDetails = authService.loadUserByUsername(username);
                    //tizimga kim kirganini set qilib qoyadi
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
                } else {
                    System.out.println("Vaqti tugagan");
                    log.error("Vaqti tugagan!");
                }
            } else {
                System.out.println("JWT token emas!");
                log.error("JWT token emas!");

            }
        }
        doFilter(request, response, filterChain);
    }
}
