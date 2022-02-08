package com.example.restapi.dao;

import com.example.restapi.entity.Course;
import com.example.restapi.entity.Student;
import com.example.restapi.entity.StudentCourse;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 * @author phunq3107
 * @since 2/5/2022
 */
@Repository
public class StudentCourseDAOImpl extends GenericDAOImpl<StudentCourse, StudentCourse.Id>
    implements StudentCourseDAO {

  public StudentCourseDAOImpl() {
    super(StudentCourse.class);
  }

}
