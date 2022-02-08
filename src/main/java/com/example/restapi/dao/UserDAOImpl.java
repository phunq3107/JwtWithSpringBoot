package com.example.restapi.dao;

import com.example.restapi.entity.User;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 * @author phunq3107
 * @since 2/7/2022
 */
@Repository
public class UserDAOImpl extends GenericDAOImpl<User, Long> implements UserDAO {

  public UserDAOImpl() {
    super(User.class);
  }

  @Override
  public User findByUsername(String username) {
    TypedQuery<User> query =
        em.createQuery("select u from User u where u.username = :username", User.class);
    query.setParameter("username", username);
    try {
      return query.getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }
}
