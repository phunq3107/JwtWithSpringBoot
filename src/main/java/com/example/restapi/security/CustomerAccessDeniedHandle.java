package com.example.restapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author phunq3107
 * @since 2/7/2022
 */
public class CustomerAccessDeniedHandle implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    Map<String, String> body = new LinkedHashMap<>() {{
      put("message", "Access denied");
      put("code", HttpStatus.FORBIDDEN.toString());
      put("path", request.getRequestURI());
    }};
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.getOutputStream().println(new ObjectMapper().writeValueAsString(body));

  }
}
