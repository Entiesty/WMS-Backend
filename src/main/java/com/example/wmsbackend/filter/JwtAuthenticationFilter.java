package com.example.wmsbackend.filter;

import com.example.wmsbackend.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {

        // 获取请求头中的 Authorization 信息
        String token = request.getHeader("Authorization");

        // 检查 token 是否以 "Bearer " 开头
        if (token != null && token.startsWith("Bearer ")) {
            // 提取 token 部分
            token = token.substring(7); // 去掉 "Bearer "，获取实际的 token

            // 使用 JwtUtils 或其他工具类解析和验证 token
            String username = jwtUtils.getUserNameFromToken(token);
            if (username != null && jwtUtils.validateToken(token)) {
                // 将认证信息放入 Spring Security 的上下文中
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>())
                );
            }
        }

        // 继续执行过滤链
        chain.doFilter(request, response);
    }
}

