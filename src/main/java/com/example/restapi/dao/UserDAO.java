package com.example.restapi.dao;

import com.example.restapi.dao.GenericDAO;
import com.example.restapi.entity.User;

/**
 * @author phunq3107
 * @since 2/7/2022
 */
public interface UserDAO extends GenericDAO<User, Long> {

  public User findByUsername(String username);

}
