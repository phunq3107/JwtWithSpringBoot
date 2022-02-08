package com.example.restapi.filter;

import com.example.restapi.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author phunq3107
 * @since 2/7/2022
 */
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final ObjectMapper mapper = new ObjectMapper();

  public CustomAuthenticationFilter(
      AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    String username = obtainUsername(request);
    String password = obtainPassword(request);
    log.info(String.format("User [ %s -- %s ] is logging!!", username, password));

    return authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password)
    );
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    log.info("Login success!!!");
//    SecurityContextHolder.getContext().setAuthentication(authResult);
    String accessToken = JwtUtils.generateAccessToken(
        (String) authResult.getPrincipal(),
        request.getRequestURL().toString(),
        authResult.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).collect(Collectors.toList())
    );
    String refreshToken = JwtUtils.generateRefreshToken(
        (String) authResult.getPrincipal(),
        request.getRequestURL().toString()
    );

    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType("application/json");
    response.getOutputStream().println(mapper.writeValueAsString(
        new LinkedHashMap<>() {{
          put("message", "Login success");
          put("access_token", accessToken);
          put("refresh_token", refreshToken);
        }}
    ));

  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
//    log.error("Login fail!!!");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.getOutputStream().println(mapper.writeValueAsString(
        new HashMap<String, String>() {{
          put("message", "Login failed");
          put("reason", failed.getMessage());
        }}
    ));
  }


}
