package com.example.restapi.dto;

import com.example.restapi.entity.Student;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * @author phunq3107
 * @since 2/6/2022
 */
@Data
public class StudentInfo {

  private Long id = null;
  private String name = null;
  private Map<String, Integer> courses = new HashMap<>();
  private Float avg = null;

  public StudentInfo(Student student) {
    if(student == null){
      return;
    }
    this.id = student.getId();
    this.name = student.getName();

    student.getStudentCourses().forEach(studentCourse -> {
      courses.put(studentCourse.getCourse().getName(), studentCourse.getMark());
    });
    if (courses.size() > 0) {
      avg = (float) courses.values().stream().reduce(0, Integer::sum) / courses.size();
    }

  }

}
