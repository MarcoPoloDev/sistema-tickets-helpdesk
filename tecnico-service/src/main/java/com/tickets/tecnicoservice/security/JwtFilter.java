package com.tickets.tecnicoservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider provider;

    public JwtFilter(JwtProvider provider) {
        this.provider = provider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        SecurityContextHolder.clearContext();
        if (authorization != null && authorization.startsWith("Bearer ")) {
            try {
                Claims claims = provider.verificar(authorization.substring(7));
                Object authorities = claims.get("authorities");
                if (claims.getSubject() != null && authorities instanceof List<?> roles &&
                        roles.stream().allMatch(s -> s instanceof String role && List.of("ROLE_ADMIN", "ROLE_TECNICO", "ROLE_USUARIO").contains(role))) {
                    var granted = roles.stream().map(s -> new SimpleGrantedAuthority((String) s)).toList();
                    var auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, granted);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (JwtException | IllegalArgumentException ex) {
                SecurityContextHolder.clearContext();
            }
        }
        chain.doFilter(request, response);
    }
}
