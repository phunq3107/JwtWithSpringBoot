package com.example.restapi.dao;

import com.example.restapi.dto.StudentInfo;
import com.example.restapi.entity.Student;
import javax.persistence.Tuple;
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
public class StudentDAOImpl extends GenericDAOImpl<Student, Long> implements StudentDAO {

  public StudentDAOImpl() {
    super(Student.class);
  }

  @Override
  public StudentInfo getStudentInfo(Long studentId) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<StudentInfo> criteria = cb.createQuery(StudentInfo.class);
    Root<Student> s = criteria.from(Student.class);
    criteria.select(
        cb.construct(
            StudentInfo.class,
            s
        )
    );
    criteria.where(
        cb.equal(s.get("id"), studentId)
    );
    TypedQuery<StudentInfo> query = em.createQuery(criteria);
    try {
      return query.getSingleResult();
    }
    catch (Exception e){
      log.error(String.format("Student %d is not exists", studentId));
      return null;
    }

  }
}
