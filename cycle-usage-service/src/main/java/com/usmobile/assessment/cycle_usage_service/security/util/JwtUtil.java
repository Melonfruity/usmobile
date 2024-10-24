package com.usmobile.assessment.cycle_usage_service.security.util;

import com.usmobile.assessment.cycle_usage_service.util.LoggerUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
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

    // Validate the token if it contains a 'userId' and is not expired
    public Boolean validateToken(String token) {
        final String userId = extractUserId(token);
        return (userId != null);
    }
}