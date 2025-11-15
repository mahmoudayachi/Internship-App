package com.example.internship_app.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class Jwtutil {
    public  String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    private String generateToken(Map<String,Object> extractClaims, UserDetails userDetails){
        return Jwts.builder().setClaims(extractClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .claim("role",userDetails.getAuthorities())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSigningKey() {
        byte[] keybytes = Decoders.BASE64.decode("66261163fe291fc9fc8249058b348933abb69c3159af8393d9e9c81086820d72eb467a5fbd98f7608e443147e44ff5c69a08bb5bf49ed491b937c9a0ab41dc0e864b9307314bc4c93c2153ee2118817e5e5c30e7b9277f82cb08507f5645fa55bd63375ea0d1bd16c982a60c2075d1f40866f259e6851d2516e17945fa127739cc0154542bd7c32422a8b53022e2b849e7774f6d30d688fe94fb513e417a164f4d3af7c2e845bc49d2858c2d1ac106f6bd8c6d39093c6f923bf3ac5d09b8c8d9485c4f855ccd8c6e023889b15fb4570991cadf4082235cb797e0e68a635f7966b67ca9b546acbac6c7e7c5c7cc80a38ae34c4bf35c3b204956bc7abc771d285c");
        return Keys.hmacShaKeyFor(keybytes);
    }
    public boolean isTokenValid(String token ,UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T>  T extractClaim(String token, Function<Claims,T> claimsResolvers) {
        final  Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }
}
