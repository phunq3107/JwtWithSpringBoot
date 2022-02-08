package com.example.restapi.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author phunq3107
 * @since 2/7/2022
 * @deprecated This handle Authentication Failure solution is not working because Spring Security
 * and Spring Web framework is not quite consistent in the way they handle the response
 */
@Deprecated
@RestControllerAdvice
public class RestResponseLoginException extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {AuthenticationException.class})
  public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e) {
    System.out.println(1);
    Map<String, String> body = new HashMap<String, String>() {{
      put("code", "401");
      put("message", e.getMessage());
    }};
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
  }

}
