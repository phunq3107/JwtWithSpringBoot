package com.example.restapi;

import com.example.restapi.service.AppService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author phunq3107
 * @since 12/27/2021
 */
@SpringBootApplication
@AllArgsConstructor
public class RestApiApplication implements CommandLineRunner{

  private final AppService service;

  public static void main(String[] args) {
    SpringApplication.run(RestApiApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    service.initial();
    service.main();
  }
}
