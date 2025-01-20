package com.kokuu.edukaizen.filter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kokuu.edukaizen.common.JWT;
import com.kokuu.edukaizen.common.UserInfoDetails;
import com.kokuu.edukaizen.dao.UserRepository;
import com.kokuu.edukaizen.entities.User;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWT jwt;
    private final UserRepository userRepository;
    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/auth/login",
            "/api/auth/register");

    public JWTAuthenticationFilter(JWT jwt, UserRepository userRepository) {
        this.jwt = jwt;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;

        if (EXCLUDED_PATHS.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("""
                        {
                            "success": false,
                            "message": "Token required"
                        }
                    """);
            return;
        }

        token = authHeader.substring(7);

        try {
            Claims claims = jwt.decodeToken(token);

            Long userId = claims.get("id", Long.class);
            String email = claims.get("email", String.class);

            Optional<User> user = userRepository.findByIdAndEmail(userId, email);

            if (user.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("""
                        {
                            "success": false,
                            "message": "User is not exist"
                        }
                        """);
                return;
            }

            UserInfoDetails userDetails = new UserInfoDetails(user.get());

            System.out.println(userDetails.getUser());

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                    {
                        "success": false,
                        "message": "Invalid token"
                    }
                    """);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
