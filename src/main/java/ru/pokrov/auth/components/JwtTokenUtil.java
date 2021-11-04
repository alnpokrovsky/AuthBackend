package ru.pokrov.auth.components;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.pokrov.auth.dtos.JwtToken;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    static final long MSEC_IN_SEC = 1000;

    @Value("${jwt.lifetime.sec}")
    private Long JWT_TOKEN_LIFETIME_SEC;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException
    {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException
    {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //check if the token has expired
    public Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public JwtToken generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username);
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException
    {
        //for retrieveing any information from token we will need the secret key
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private JwtToken doGenerateToken(Map<String, Object> claims, String subject) {
        Date currentTime = new Date();
        Date expireTime = new Date(currentTime.getTime() + JWT_TOKEN_LIFETIME_SEC * MSEC_IN_SEC);
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(currentTime)
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
        return new JwtToken(token, JWT_TOKEN_LIFETIME_SEC, expireTime);
    }
}
