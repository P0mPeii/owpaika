package com.owpai.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 * 用于生成和解析JWT令牌
 */
public class JwtUtil {
    /**
     * 生成JWT令牌
     * 使用HS256算法和提供的密钥生成JWT令牌
     *
     * @param secretKey JWT密钥，用于签名
     * @param ttlMillis JWT令牌的有效期（毫秒）
     * @param claims    需要在JWT中存储的信息（载荷）
     * @return 生成的JWT令牌字符串
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 指定签名算法为HS256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 计算令牌过期时间
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        // 确保密钥长度至少为256位（32字节），不足则补齐
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 32) {
            byte[] newKey = new byte[32];
            System.arraycopy(keyBytes, 0, newKey, 0, Math.min(keyBytes.length, 32));
            keyBytes = newKey;
        }

        // 构建JWT令牌
        JwtBuilder builder = Jwts.builder()
                // 设置载荷部分，包含自定义信息
                .setClaims(claims)
                // 使用指定的签名算法和密钥进行签名
                .signWith(Keys.hmacShaKeyFor(keyBytes), signatureAlgorithm)
                // 设置过期时间
                .setExpiration(exp);

        // 生成JWT字符串
        return builder.compact();
    }

    /**
     * 解析JWT令牌
     * 使用密钥验证JWT令牌的签名并解析其内容
     *
     * @param secretKey JWT密钥，用于验证签名
     * @param token     需要解析的JWT令牌字符串
     * @return JWT中的载荷信息（Claims）
     */
    public static Claims parseJWT(String secretKey, String token) {
        // 确保密钥长度至少为256位（32字节），不足则补齐
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 32) {
            byte[] newKey = new byte[32];
            System.arraycopy(keyBytes, 0, newKey, 0, Math.min(keyBytes.length, 32));
            keyBytes = newKey;
        }

        // 解析JWT令牌
        Claims claims = Jwts.parser()
                // 设置验证签名的密钥
                .setSigningKey(Keys.hmacShaKeyFor(keyBytes))
                // 解析JWT字符串
                .parseClaimsJws(token).getBody();
        return claims;
    }

}
