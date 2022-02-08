package com.example.restapi.security;

import com.example.restapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author phunq3107
 * @since 2/7/2022
 */
@Component
@AllArgsConstructor
@Slf4j
public class AuthProvider implements AuthenticationProvider {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
    String username = (String) token.getPrincipal();
    String password = (String) token.getCredentials();

    log.info(String.format("Account: %s -- %s", username, password));

    UserDetails userDetails = userService.loadUserByUsername(username);

    if (userDetails == null) {
      throw new UsernameNotFoundException(String.format("Username [%s] is not exists", username));
    }
    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new BadCredentialsException(String.format("Wrong password for username [%s]", username));
    }

    log.info(String.format("Account: %s -- %s login", username, password));

    return new UsernamePasswordAuthenticationToken(
        userDetails.getUsername(),
        userDetails.getPassword(),
        userDetails.getAuthorities()
    );
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
