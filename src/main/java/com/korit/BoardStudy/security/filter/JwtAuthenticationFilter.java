package com.korit.BoardStudy.security.filter;

import com.korit.BoardStudy.security.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements Filter {

    @Autowired
    private JwtUtils jwtUtils;



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        List<String> methods = List.of("POST", "GET", "PUT", "PATCH", "DELETE");
        if (!methods.contains(request.getMethod())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String authorization = request.getHeader("Authorization");
        if (jwtUtils.isBearer(authorization)) {
            String accessToken = jwtUtils.removeBearer(authorization);

            try {
                Claims claims = jwtUtils.getClaims(accessToken);
                String id = claims.getId();
                Integer userId = Integer.parseInt(id);

            }
        }
    }
}
