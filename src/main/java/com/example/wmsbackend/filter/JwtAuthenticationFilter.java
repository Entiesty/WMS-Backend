package com.example.wmsbackend.filter;

import com.example.wmsbackend.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        try {

            // 获取请求头中的 Authorization 信息
            String header = request.getHeader("authorization");
            System.out.println("前端返回的token为: " + header);

            // 检查 Authorization 是否以 "Bearer " 开头
            if (header != null && header.startsWith("Bearer ")) {
                // 提取实际的 token 部分
                String token = header.substring(7).trim(); // 去掉 "Bearer " 前缀，并清理空格

                // 验证 Token 并获取用户名
                if (!token.isEmpty() && !jwtUtils.validateTokenExpired(token)) {
                    String username = jwtUtils.getUserNameFromToken(token);

                    // 确保用户名不为空，设置认证信息到 Spring Security 上下文
                    if (username != null) {
                        SecurityContextHolder.getContext().setAuthentication(
                                new UsernamePasswordAuthenticationToken(
                                        username, null, new ArrayList<>()
                                )
                        );
                    }
                }
            }
        } catch (MalformedJwtException e) {
            // Token 格式错误
            log.error("Invalid JWT format: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            // Token 已过期
            log.warn("Expired JWT: {}", e.getMessage());
        } catch (Exception e) {
            // 其他异常
            log.error("Failed to authenticate JWT: {}", e.getMessage(), e);
        }

        // 继续执行过滤链
        chain.doFilter(request, response);
    }
}


