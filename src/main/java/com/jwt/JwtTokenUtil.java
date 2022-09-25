package com.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;


@Service
@ConfigurationProperties(prefix = "jwt.token")
@Getter
@Setter
public class JwtTokenUtil {

    //    @Value("Java-6")
    private String secret;

    private String issuer;


    private long expires;

    public String generateToken(String email) {

        Date expirationDate = Date.from(ZonedDateTime.now().plusDays(60).toInstant());

        return JWT.create()
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    // validate token
    public String validateJWTToken(String jwt) {
        DecodedJWT verify = getDecodedJWT(jwt);

        return verify.getClaim("email").asString();
    }

    private DecodedJWT getDecodedJWT(String jwt) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.
                        HMAC256(secret))
                .withIssuer(issuer)
                .build();

        return jwtVerifier.verify(jwt);
    }

    public LocalDateTime getIssuedAt(String jwt) {
        DecodedJWT decodedJWT = getDecodedJWT(jwt);

        return LocalDateTime.ofInstant(
                decodedJWT.getIssuedAt().toInstant(),
                ZoneId.systemDefault()
        );
    }


    // I added two methods in WEB;


//    private Claims getAllClaimsFromToken(String token) {
//            return Jwts.parser().setSigningKey(jwtSecret.getBytes(Charset.forName("UTF-8"))).
//                    parseClaimsJws(token.replace("{", "").replace("}","")).getBody();
//        }
//
//    private String doGenerateToken(Map<String, Object> claims, String subject) {
//
//            return Jwts.builder().
//                    setClaims(claims).
//                    setSubject(subject).
//                    setIssuedAt(new Date(System.currentTimeMillis()))
//                    .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//                    .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes(Charset.forName("UTF-8"))).compact();
//        }


//
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//
//    public String getUsernameFromToken(String token) {
//        return getClaimFromToken(token, Claims::getSubject);
//    }
//
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username  = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername())&& ! isTokenExpired(token));
//    }
}
