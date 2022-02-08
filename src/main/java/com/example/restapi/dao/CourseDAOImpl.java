package com.example.restapi.dao;

import com.example.restapi.dto.CourseSummary;
import com.example.restapi.entity.Course;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 * @author phunq3107
 * @since 2/3/2022
 */
@Repository
public class CourseDAOImpl extends GenericDAOImpl<Course, String> implements CourseDAO {

  public CourseDAOImpl() {
    super(Course.class);
  }

  @Override
  public List<Course> findByName(String name) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Course> criteria = cb.createQuery(Course.class);
    Root<Course> c = criteria.from(Course.class);
    criteria.select(c);
    criteria.where(
        cb.equal(c.get("name"), name)
    );
    TypedQuery<Course> query = em.createQuery(criteria);
    return query.getResultList();
  }

  @Override
  public List<Course> findByNameLike(String substring) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Course> criteria = cb.createQuery(Course.class);
    Root<Course> c = criteria.from(Course.class);
    criteria.select(c);
    criteria.where(
        cb.like(c.get("name"), "%" + substring + "%")
    );
    TypedQuery<Course> query = em.createQuery(criteria);
    return query.getResultList();
  }

  @Override
  public List<Course> findByTeacherName(String teacherName) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Course> criteria = cb.createQuery(Course.class);
    Root<Course> c = criteria.from(Course.class);
    criteria.select(c);
    criteria.where(
        cb.equal(c.get("teacher").get("name"), teacherName)
    );

    TypedQuery<Course> query = em.createQuery(criteria);
    return query.getResultList();
  }

  @Override
  public List<CourseSummary> findCourseSummaries() {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<CourseSummary> criteria = cb.createQuery(CourseSummary.class);
    Root<Course> c = criteria.from(Course.class);
    criteria.select(
        cb.construct(
            CourseSummary.class,
            c.get("id"),
            c.get("name"),
            c.get("teacher").get("name"),
            cb.size(c.get("studentCourses")),
            c.get("price")
        )
    );
    TypedQuery<CourseSummary> query = em.createQuery(criteria);
    return query.getResultList();

  }
}
