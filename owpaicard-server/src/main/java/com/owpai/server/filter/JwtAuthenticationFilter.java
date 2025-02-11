package com.owpai.server.filter;

import com.owpai.common.constant.JwtClaimsConstant;
import com.owpai.common.properties.JwtProperties;
import com.owpai.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT认证过滤器
 * 用于处理携带JWT令牌的请求，验证令牌有效性并设置认证信息
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 过滤器核心处理方法
     * 
     * @param request     HTTP请求对象
     * @param response    HTTP响应对象
     * @param filterChain 过滤器链
     * @throws ServletException Servlet异常
     * @throws IOException      IO异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {

        // 从请求头中获取token
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        if (token != null && !token.isEmpty()) {
            try {
                // 解析JWT令牌
                Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
                // 从令牌中获取管理员ID
                Long adminId = Long.valueOf(claims.get(JwtClaimsConstant.ADMIN_ID).toString());

                // 创建认证对象，并设置用户权限
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        adminId, // 认证主体（用户ID）
                        null, // 凭证（密码，这里不需要）
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")) // 授权列表
                );

                // 将认证信息存入SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Token解析失败，不设置认证信息，继续处理请求
            }
        }

        // 继续执行过滤器链
        filterChain.doFilter(request, response);
    }
}