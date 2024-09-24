package com.aceros_duran.pos.authenticationProvider.jwt.service;

import com.aceros_duran.pos.authenticationProvider.model.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;


@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String key;
    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    public String getToken(UserModel user) {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(HashMap<String, Object> extraClaims, UserModel user){

        extraClaims.put("role", user.getAuthorities());
        extraClaims.put("uuid", user.getIdAsString());

        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + Long.valueOf(timeExpiration)))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey(){

        byte[] byteKey = Decoders.BASE64.decode(this.key);

        return Keys.hmacShaKeyFor(byteKey);

    }

    public String getUsernameFromToken(String token) {
        return getClaims(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {

        final String username = getUsernameFromToken(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));


    }

    private Claims getAllClaims(String token){
        return Jwts.
                parser()
                .verifyWith(this.getKey())
                .build().parseSignedClaims(token).getPayload();
    }

    public <T> T getClaims(String token, Function<Claims,T> claimsResolver){

        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);

    }

    private Date getExpiration(String token){
        return getClaims(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }

}