package com.example.restapi.resources;

import com.example.restapi.jwt.JwtUtils;
import com.example.restapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author phunq3107
 * @since 2/8/2022
 */
@RestController
@RequestMapping(path = {"/api/jwt"})
public class JwtToken {

  private final ObjectMapper mapper = new ObjectMapper();
  private final UserService userService;

  public JwtToken(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/refresh")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    response.setContentType("application/json");
    if (JwtUtils.isJwtAuthorization(authorizationHeader)) {
      try {
        Authentication authentication = JwtUtils.verify(authorizationHeader);
        UserDetails user = userService.loadUserByUsername((String) authentication.getPrincipal());
        String accessToken = JwtUtils.generateAccessToken(
            user.getUsername(),
            request.getRequestURL().toString(),
            user.getAuthorities().stream().map(GrantedAuthority::toString)
                .collect(Collectors.toList())
        );
        String refreshToken = JwtUtils.getToken(authorizationHeader);
        response.setStatus(HttpServletResponse.SC_OK);

        response.getOutputStream().println(mapper.writeValueAsString(
            new LinkedHashMap<>() {{
              put("message", "Refresh token success");
              put("access_token", accessToken);
              put("refresh_token", refreshToken);
            }}
        ));

      } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getOutputStream().println(mapper.writeValueAsString(
            new TreeMap<>() {{
              put("error_message", e.getMessage());
            }}
        ));
      }
    } else {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getOutputStream().println(mapper.writeValueAsString(
          new TreeMap<>() {{
            put("error_message", "Refresh token is required");
          }}
      ));
    }
  }
}
