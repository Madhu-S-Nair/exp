package com.exp.service;

import com.exp.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {


    public String generateToken(Users user) {
        Map<String, Objects> claims = new HashMap<>();

        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(user.getUsername())
                .issuer("exp")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*10*1000))
                .and()
                .signWith(generateKey())
                .compact();

    }

    private SecretKey generateKey() {
      byte[] decode =  Decoders.BASE64.decode(getSecretKey());

      return Keys.hmacShaKeyFor(decode);
    }


    public String getSecretKey(){
        return "d89b119ca0c0718724a5928cb7ad6d3b74c7d1d6b04e666a3a56bcee8143f9a7";
    }

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {

        Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    private  Claims extractClaims(String token){
        return Jwts
                .parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        
        
        return  (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return returnExpiration(token).before(new Date());
    }

    private Date returnExpiration(String token) {
        return  extractClaims(token,Claims::getExpiration);
    }
}
