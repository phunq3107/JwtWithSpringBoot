package com.example.restapi.filter;

import com.example.restapi.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author phunq3107
 * @since 2/8/2022
 */
@Slf4j
public class CustomAccessControlFilter extends OncePerRequestFilter {


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (!request.getServletPath().equals("/api/login")) {
      String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
      if (JwtUtils.isJwtAuthorization(authorizationHeader)) {
        try {
          Authentication authentication = JwtUtils.verify(authorizationHeader);
          SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
          log.error(e.getMessage());
          response.setContentType("application/json");
          response.setStatus(HttpServletResponse.SC_FORBIDDEN);
          response.getOutputStream().println(
              new ObjectMapper().writeValueAsString(
                  new TreeMap<>() {{
                    put("error_message", e.getMessage());
                  }}
              )
          );
          return;
        }
      }
    }
    filterChain.doFilter(request, response);


  }
}
