package net.exampleproject.ems.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import net.exampleproject.ems.entity.Employee;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
@Component
public class JwtUtil {
    private static final String SECRET_KEY_STRING= "dxhe1aNbMtkGCiCVFHGBvQwPnSViJs0L";

    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    public String generateToken(UserDetails userDetails, Employee employee){
        return Jwts.builder().subject(userDetails.getUsername())
                .claim("empid",employee.getEmpid())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SECRET_KEY,Jwts.SIG.HS256)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername());
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Long extractEmpId(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("empid", Long.class);  // Extracting the empid claim
    }

    public String generateRefreshToken(UserDetails userDetails, Employee employee) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("empid", employee.getEmpid())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 days
                .signWith(SECRET_KEY, Jwts.SIG.HS256)
                .compact();
    }

    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expiration.before(new Date());
    }


}
