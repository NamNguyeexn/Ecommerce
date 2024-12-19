package com.iden.services.impl;

import com.iden.models.Account;
import com.iden.services.IJwtTokenService;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JwtTokenService implements IJwtTokenService {
    private final String SECRET_KEY = "2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D2D4A614E645267" +
            "556B58703273357638792F423F4428472B4B6250655368566D";
    private final String ISSUER = "20240719";
    @Override
    public String generateToken(Account account) {
        Map<String,String> claims = new HashMap<>();
        claims.put("accountId", String.valueOf(account.getId()));
        claims.put("accountUN", account.getUsername());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 8 * 60 * 60 * 1000)) // 8 hours
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    @Override
    public Integer getAccountId(String token) {
        Claims claims = getClaims(token);
        String userId = (String) claims.get("accountId");
//        ThreadContext.put("accountId", "accountId");
        return Integer.parseInt(userId);
    }

    @Override
    public String getUsernameByRequest(HttpServletRequest request) {
        return getUsernameByToken(
                request
                        .getHeader(HttpHeaders.AUTHORIZATION)
                        .split(" ")[1].trim()
        );
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

    @Override
    public String getTokenFromRequest(HttpServletRequest request) {
        return request
                .getHeader(HttpHeaders.AUTHORIZATION)
                .split(" ")[1].trim();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
    private String getUsernameByToken(String token) {
        Claims claims = getClaims(token);
        String username = (String) claims.get("accountUN");
        ThreadContext.put("accountUN", "accountUN");
        return username;
    }
}
