package com.example.restapi.dao;

import com.example.restapi.dto.TeacherInfo;
import com.example.restapi.entity.Teacher;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * @author phunq3107
 * @since 2/5/2022
 */
@Repository
@Slf4j
public class TeacherDAOImpl extends GenericDAOImpl<Teacher, Long> implements TeacherDAO {

  public TeacherDAOImpl() {
    super(Teacher.class);
  }

  @Override
  public TeacherInfo getTeacherInfo(Long teacherId) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<TeacherInfo> criteria = cb.createQuery(TeacherInfo.class);
    Root<Teacher> t = criteria.from(Teacher.class);
    criteria.select(
        cb.construct(
            TeacherInfo.class,
            t
        )
    );
    criteria.where(cb.equal(t.get("id"), teacherId));

    TypedQuery<TeacherInfo> query = em.createQuery(criteria);
    try {
      return query.getSingleResult();
    }
    catch (Exception e){
      log.error(String.format("Teacher %d is not exists", teacherId));
      return null;
    }
  }
}
