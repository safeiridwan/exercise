package com.backend.master.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.backend.master.errors.AuthoritationException;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.backend.master.constants.SecurityConstants.*;

@Service
public class JwtUtil {

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(KEY.getBytes())).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date exp = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        Key key = Keys.hmacShaKeyFor(KEY.getBytes());
        String token = Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(exp).signWith(key,SignatureAlgorithm.HS512).compact();

        return token;
    }

    public Boolean validate(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(KEY.getBytes()).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException e) {
			throw new AuthoritationException("Invalid JWT token: {}", HttpStatus.UNAUTHORIZED);
		} catch (ExpiredJwtException e) {
			throw new AuthoritationException("JWT token is expired: {}", HttpStatus.UNAUTHORIZED);
		} catch (UnsupportedJwtException e) {
			throw new AuthoritationException("JWT token is unsupported: {}", HttpStatus.UNAUTHORIZED);
		} catch (IllegalArgumentException e) {
			throw new AuthoritationException("JWT claims string is empty: {}", HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			throw new AuthoritationException("Invalid JWT signature: {}", HttpStatus.UNAUTHORIZED);
		}
	}
}
