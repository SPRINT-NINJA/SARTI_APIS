package mx.edu.utez.SARTI_APIS.securiy_config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import mx.edu.utez.SARTI_APIS.securiy_config.model.AuthUser;

import java.security.Key;
import java.util.Date;

@Service
public class JwtProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_TYPE = "Bearer ";

    public String generatedToken(Authentication authentication){
        AuthUser user = (AuthUser) authentication.getPrincipal();
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("role", user.getAuthorities());
        Date tokenCreatedTime = new Date();
        Date validTime = new Date(tokenCreatedTime.getTime() * expiration * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(validTime)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims parseJwtClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null)
                return parseJwtClaims(token);
            return Jwts.claims();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            LOGGER.error("Invalid JWT token", e);
            return Jwts.claims();
        }
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_TYPE))
            return bearerToken.replace(TOKEN_TYPE, "");
        return null;
    }

    public boolean validateClaims(Claims claims, String token){
        try{
            parseJwtClaims(token);
            return claims.getExpiration().after(new Date());
        }catch (MalformedJwtException | UnsupportedJwtException | ExpiredJwtException e){
            LOGGER.error("Invalid JWT token", e);
        }
        return false;
    }
}
