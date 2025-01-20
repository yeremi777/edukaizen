package com.kokuu.edukaizen.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWT {
    private final SecretKey key;
    private final long expirationMillis;

    public JWT(@Value("${jwt.secret-key}") String secret, @Value("${jwt.expiration-time}") String expirationTime)
            throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedKey = digest.digest(secret.getBytes(StandardCharsets.UTF_8));

        this.key = Keys.hmacShaKeyFor(hashedKey);
        this.expirationMillis = parseExpiration(expirationTime);
    }

    private long parseExpiration(String expiration) {
        char unit = expiration.charAt(expiration.length() - 1);
        int value = Integer.parseInt(expiration.substring(0, expiration.length() - 1));

        switch (Character.toLowerCase(unit)) {
            case 'd':
                return TimeUnit.DAYS.toMillis(value);
            case 'h':
                return TimeUnit.HOURS.toMillis(value);
            case 'm':
                return TimeUnit.MINUTES.toMillis(value);
            case 's':
                return TimeUnit.SECONDS.toMillis(value);
            default:
                throw new IllegalArgumentException("Invalid expiration format: " + expiration);
        }
    }

    public String signToken(Long id, String email) {
        return Jwts.builder()
                // .header().add("typ", "JWT").and()
                .claims(Map.of("id", id, "email", email))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + this.expirationMillis))
                .signWith(this.key, Jwts.SIG.HS256)
                .compact();
    }

    public Claims decodeToken(String token) {
        return Jwts.parser()
                .verifyWith(this.key)
                .build()
                .parseSignedClaims(token).getPayload();
    }

    public Date extractExpiration(String token) {
        Claims claims = decodeToken(token);

        return claims.getExpiration();
    }
}
