package com.example.restapi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * @author phunq3107
 * @since 2/8/2022
 */
public class JwtUtils {

  private static final String SECRET_KEY = "b42321305fc14bc69ae2bbe6c8ea99f7";
  private static final Integer ACCESS_TOKEN_EXPIRED_TIME = 30 * 1000;
  private static final Integer REFRESH_TOKEN_EXPIRED_TIME = 30 * 60 * 1000;
  private static final String ROLE_KEY = "roles";
  private static final String JWT_TOKEN_STARTING_KEY = "Bearer ";
  private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());

  public static String generateAccessToken(String subject, String issuer, List<?> roles) {
    return JWT.create()
        .withSubject(subject)
        .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRED_TIME))
        .withIssuer(issuer)
        .withClaim(ROLE_KEY, roles)
        .sign(algorithm);
  }

  public static String generateRefreshToken(String subject, String issuer) {
    return JWT.create()
        .withSubject(subject)
        .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRED_TIME))
        .withIssuer(issuer)
        .sign(algorithm);
  }

  public static boolean isJwtAuthorization(String authorization) {
    return authorization != null && authorization.startsWith(JWT_TOKEN_STARTING_KEY);
  }

  public static String getToken(String authorization) {
    return authorization.substring(JWT_TOKEN_STARTING_KEY.length());
  }

  public static Authentication verify(String authorization) throws Exception {
    String token = getToken(authorization);
    JWTVerifier verifier = JWT.require(algorithm).build();
    DecodedJWT decodedJWT = verifier.verify(token);

    String username = decodedJWT.getSubject();
    String[] roles = decodedJWT.getClaim(ROLE_KEY).asArray(String.class);
    List<GrantedAuthority> authorities = null;
    if (roles != null) {
      authorities = Arrays
          .stream(roles)
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toList());
    }

    return new UsernamePasswordAuthenticationToken(
        username,
        null,
        authorities
    );
  }


}
