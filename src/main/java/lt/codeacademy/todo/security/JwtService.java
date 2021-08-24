package lt.codeacademy.todo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lt.codeacademy.todo.entities.Role;
import lt.codeacademy.todo.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private byte[] secret;

    @Value("${security.jwt.validity-min}")
    private long validityMin;

    public String createToken(User user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer("car-api")
                .setAudience("car-ui")
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityMin * 60000))
                // "roles": ["ROLE_ADMIN", "ROLE_USER"]
                .claim("roles", user.getRoles().stream().map(Role::getAuthority).collect(toSet()))
                .signWith(Keys.hmacShaKeyFor(secret), SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication parseToken(String jwt) {
        Claims parsedJwtBody;

        try {
            parsedJwtBody = Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            return null;
        }

        String username = parsedJwtBody.getSubject();
        List<GrantedAuthority> roles =
                ((List<String>) parsedJwtBody.get("roles")).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username, null, roles);
    }
}
