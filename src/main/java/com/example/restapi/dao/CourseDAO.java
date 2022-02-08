package com.example.restapi.dao;

import com.example.restapi.dto.CourseSummary;
import com.example.restapi.entity.Course;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author phunq3107
 * @since 2/3/2022
 */
public interface CourseDAO extends GenericDAO<Course, String> {

  public List<Course> findByName(String name);

  public List<Course> findByNameLike(String substring);

  public List<Course> findByTeacherName(String teacherName);

  public List<CourseSummary> findCourseSummaries();

}
