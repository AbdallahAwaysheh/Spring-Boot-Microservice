package com.orange.e_shop.user_service.filter;

import com.orange.e_shop.user_service.service.auth.JwtService;
import com.orange.e_shop.user_service.service.auth.UserDetailsServiceImpl;
import com.orange.e_shop.user_service.service.auth.TokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final ApplicationContext context;

    private final TokenBlacklistService tokenBlacklistService;

    public JwtFilter(JwtService jwtService, ApplicationContext context, TokenBlacklistService tokenBlacklistService) {
        this.jwtService = jwtService;
        this.context = context;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException,
                                                                       IOException
    {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            if (tokenBlacklistService.isBlacklisted(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token has been invalidated");
                return;
            }
            userName = jwtService.extractUserName(token);
        }
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            var userDetails = context.getBean(UserDetailsServiceImpl.class).loadUserByUsername(userName);

            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);

    }
}
