package com.thusith.booking;

import com.thusith.booking.modal.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

/*
  Author -: Thusith Wickramasinghe @github/ThusithDev
*/

@Component
public class JwtUtil {

    @Value("${JWT_SECRET}") // Load secret key from application.properties
    private String secret;

    // Generate Session Token
    public String generateToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())  // Adding userId to token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }


    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token, String userEmail) {
        return (extractEmail(token).equals(userEmail) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claimsResolver.apply(claims);
    }

}
