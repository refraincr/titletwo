package com.uunnm.titletwo.business.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expireSeconds}")
    private int expireSeconds;

    private SecretKey getKey() {
        // 签名密钥
        byte[] keyBytes = Base64.getDecoder().decode(secretKey.getBytes());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getToken(String username) {
        return Jwts.builder()
                .claims()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ expireSeconds * 1000L))
                .and()
                .signWith(getKey())
                .compact();
    }

    public String extraUsername(String token) {
        return extraClaims(token).getSubject();
    }

    private Claims extraClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean valid(String token, UserDetails userDetails) {
        // 验证时效性和用户名
        try {
            Claims claims = extraClaims(token);

            String username = claims.getSubject();

            return userDetails.getUsername().equals(username) &&
                    !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
