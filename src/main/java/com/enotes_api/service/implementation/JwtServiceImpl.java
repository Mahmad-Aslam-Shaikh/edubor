package com.enotes_api.service.implementation;

import com.enotes_api.entity.UserEntity;
import com.enotes_api.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    private String secretKey;

    public JwtServiceImpl() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey key = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error generating Secret Key");
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateToken(UserEntity userEntity) {
        String jwtToken = Jwts.builder()
                .claims().add(getClaims(userEntity))
                .subject(userEntity.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1)) // 1 hour
                .and()
                .signWith(getKey())
                .compact();
        return jwtToken;
    }

    private Map<String, ?> getClaims(UserEntity userEntity) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("userRoles", userEntity.getRoles());
        claims.put("isActive", userEntity.getIsActive());
        return claims;
    }

    private SecretKey getKey() {
        byte[] secretKeyAsBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretKeyAsBytes);
    }

    @Override
    public String extractUserEmailFromToken(String token) {
        Claims claims = extractAllClaimsFromToken(token);
        return claims.getSubject();
    }

    private Claims extractAllClaimsFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
//                .verifyWith(decryptKey(secretKey))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims;
    }

/*
    For now decryptKey is not required because getKey also return the same secret key hence reusing this
 */
//    private SecretKey decryptKey(String secretKey) {
//        byte[] secretKeyAsBytes = Decoders.BASE64.decode(secretKey);
//        return Keys.hmacShaKeyFor(secretKeyAsBytes);
//    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        boolean isTokenExpired = isTokenExpired(token);
        String userEmailFromToken = extractUserEmailFromToken(token);
        return userEmailFromToken.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired;
    }

    private boolean isTokenExpired(String token) {
        Claims claims = extractAllClaimsFromToken(token);
        Date tokenExpirayDate = claims.getExpiration();
        return tokenExpirayDate.before(new Date());
    }

}
