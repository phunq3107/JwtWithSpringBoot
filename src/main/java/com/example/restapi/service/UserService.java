package com.example.restapi.service;

import com.example.restapi.dao.UserDAO;
import com.example.restapi.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author phunq3107
 * @since 2/7/2022
 */
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

  private final UserDAO userDAO;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username)  {
    return userDAO.findByUsername(username);
  }

  public User createUser(User user){
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userDAO.makePersistence(user);
  }


}
