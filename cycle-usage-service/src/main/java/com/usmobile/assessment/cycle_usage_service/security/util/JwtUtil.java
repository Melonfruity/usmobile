package com.usmobile.assessment.cycle_usage_service.security.util;

import com.usmobile.assessment.cycle_usage_service.util.LoggerUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String base64EncodedKey;

    @Value("${jwt.expiration}")
    private Integer exp;

    // Create Signing Key
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64EncodedKey));
    }

    // Extract the 'userId' from the token
    public String extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", String.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
        LoggerUtil.logDebug("Extracting From Token: ", token);
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (SignatureException e) {
            // Handle invalid JWT signature
            throw new RuntimeException("Invalid JWT signature: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            throw new RuntimeException("Unable to extract claims: " + e.getMessage());
        }
    }

    // Generate token with userId
    public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);  // Add userId to the token
        return createToken(claims);
    }

    private String createToken(Map<String, Object> claims) {
        LoggerUtil.logDebug("Creating Token");
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + exp))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate the token if it contains a 'userId' and is not expired
    public Boolean validateToken(String token) {
        final String userId = extractUserId(token);
        return (userId != null);
    }
}