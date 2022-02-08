package com.example.restapi.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author phunq3107
 * @since 2/7/2022
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {

    Map<String, String> body = new HashMap<>() {{
      put("message", authException.getMessage());
    }};

    System.out.println(authException.getMessage());
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.getOutputStream().println(authException.getMessage());

  }
}
