package com.example.restapi.dao;

import com.example.restapi.entity.User;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import org.springframework.validation.ObjectError;

/**
 * @author phunq3107
 * @since 2/3/2022
 */
public interface GenericDAO<T, ID extends Serializable> {

  public void setEntityManager(EntityManager em);

  public T makePersistence(T instance);

  public void makeTransient(T instance);

  public T findById(ID id);

  public T findById(ID id, LockModeType lockModeType);

  public T findReferenceById(ID id);

  public List<T> findAll();

  public Long getCount();

}
