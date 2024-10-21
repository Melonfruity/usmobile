package com.usmobile.assessment.cycle_usage_service.security.util;

import com.usmobile.assessment.cycle_usage_service.util.LoggerUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {


    private static SecretKey secret;

    // Create Signing Key
    public JwtUtil() {
//        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String base64EncodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
//        secret = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64EncodedKey));
        secret = Keys.hmacShaKeyFor((Base64.getDecoder().decode("Jx61cxrEab8EftvieKvhxeAkx84yOmw6/OQc10XfSfU=")));
        LoggerUtil.logInfo("Generated secret", "Jx61cxrEab8EftvieKvhxeAkx84yOmw6/OQc10XfSfU="); // TODO REMOVE
    }

    // Extract the 'userId' from the token
    public static String extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", String.class));
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        try {
        LoggerUtil.logDebug("Extracting From Token: ", token);
        return Jwts.parserBuilder()
                .setSigningKey(secret)
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
    public static String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);  // Add userId to the token
        return createToken(claims);
    }

    private static String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours validity
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate the token if it contains a 'userId' and is not expired
    public static Boolean validateToken(String token) {
        final String userId = extractUserId(token);
        return (userId != null);
    }

    // TODO - Token Expiration is not implementd
}