package com.orange.e_shop.user_service.service.auth;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private final SecretKey resetSecretKey = Keys.hmacShaKeyFor("idkwhattouseforthissecret1231235123".getBytes());

    public String generateResetToken(String email) {

        log.info("email before token gen " + email);
        return Jwts.builder()
                .setSubject(email)
                .claim("type", "password_reset")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(resetSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateResetToken(String token, String expectedEmail) {
        log.info("Validating token against expected email: {}", expectedEmail);

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(resetSecretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (!"password_reset".equals(claims.get("type"))) {
                log.info("Invalid token type");
                return false;
            }

            String subject = claims.getSubject();
            log.info("Extracted subject: [{}]", subject);

            return expectedEmail.equals(subject.replace("\"", "")); // In case it's quoted like \"email\"
        } catch (JwtException e) {
            log.warn("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(Map.of("role", userDetails.getAuthorities().iterator().next().getAuthority()))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 24*60*60*1000))//24 hours
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new java.util.Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        return validateToken(token, userDetails);
    }

    public Date getExpirationDateFromToken(String token) {
        return extractExpiration(token);
    }
}