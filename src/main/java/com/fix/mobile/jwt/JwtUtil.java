package com.fix.mobile.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JwtUtil {
    private static final String admin = "ROLE_ADMIN";
    private static final String staff = "ROLE_STAFF";
    private static final String user = "ROLE_USER";
    private static final String guest = "ROLE_GUEST";

    private String secret;

    private int jwtExpirationInMs;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }
    @Value("${jwt.jwtExpirationInMs}")
    public void setJwtExpirationInMs(int jwtExpirationInMs) {
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    public String GenerateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        if (roles.contains(new SimpleGrantedAuthority(admin))){
            claims.put("admin", true);
        }
        if (roles.contains(new SimpleGrantedAuthority(staff))){
            claims.put("staff", true);
        }
        if (roles.contains(new SimpleGrantedAuthority(user))){
            claims.put("user", true);
        }
        if (roles.contains(new SimpleGrantedAuthority(guest))){
            claims.put("guest", true);
        }
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }

    public String getUsernameByToken(String token){
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public List<SimpleGrantedAuthority> getRoleByToken(String token){
        List<SimpleGrantedAuthority> roles = null;
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        Boolean isAdmin = claims.get("admin", Boolean.class);
        Boolean isStaff = claims.get("staff", Boolean.class);
        Boolean isUser = claims.get("user", Boolean.class);
        Boolean isGuest = claims.get("guest", Boolean.class);

        if (isAdmin != null && isAdmin == true){
            roles = Arrays.asList(new SimpleGrantedAuthority(admin));
        }
        if (isStaff != null && isStaff == true){
            roles = Arrays.asList(new SimpleGrantedAuthority(staff));
        }
        if (isUser != null && isUser == true){
            roles = Arrays.asList(new SimpleGrantedAuthority(user));
        }
        if (isGuest != null && isGuest == true){
            roles = Arrays.asList(new SimpleGrantedAuthority(guest));
        }

        return roles;
    }

    public boolean validateToken(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException e){
            throw e;
        }
    }
}
