package com.example.restapi.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * Working with form login
 *
 * @author phunq3107
 * @since 2/7/2022
 */
public class CustomAuthenticationFailureHandle implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    Map<String, String> body = new HashMap<>() {{
      put("message", exception.getMessage());
    }};
    System.out.println(1);

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.getOutputStream().println(body.toString());

  }
}
