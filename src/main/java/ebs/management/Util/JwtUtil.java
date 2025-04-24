package ebs.management.Util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private Key key;

    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    @PostConstruct
    public void init() {
        // Automatically generates a secure random key compatible with HS384
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS384); // Use HS384 key
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS384) // Sign with the same key
                .compact();
    }

    public String extractUsername(String token) {
        if (token == null || token.trim().isEmpty()) return null;
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null; // Or throw a custom exception if you prefer
        }
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // Use the same key for parsing
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (JwtException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key) // Use the same key for parsing
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
