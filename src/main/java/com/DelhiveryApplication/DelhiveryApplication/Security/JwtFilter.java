package com.DelhiveryApplication.DelhiveryApplication.Security;

import com.DelhiveryApplication.DelhiveryApplication.Service.AgentDetailsServiceImpl;
import com.DelhiveryApplication.DelhiveryApplication.Service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final AgentDetailsServiceImpl agentDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        if (path.equals("/user/createUser") || path.equals("/user/verifyUser") || path.equals("/login") ||
                path.equals("/agent/createAccount") || path.equals("/agent/verifyAgent")){
            filterChain.doFilter(request,response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String role ;
        String email;

        try {
            email = jwtService.extractEmail(token);
            role = jwtService.getbody(token).get("role", String.class);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails;

        if (role.equals("ROLE_USER")){
            userDetails = userDetailsService.loadUserByUsername(email);
        }else{
            userDetails = agentDetailsService.loadUserByUsername(email);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);

    }
}
