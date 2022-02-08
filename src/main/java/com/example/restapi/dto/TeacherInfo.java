package com.example.restapi.dto;

import com.example.restapi.entity.Teacher;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * @author phunq3107
 * @since 2/6/2022
 */
@Data
public class TeacherInfo {

  private Long id = null;
  private String name = null;
  private List<String> courses = new ArrayList<>();

  public TeacherInfo(Teacher teacher) {
    if (teacher == null) {
      return;
    }
    this.id = teacher.getId();
    this.name = teacher.getName();
    teacher.getCourses().forEach(course -> courses.add(course.getName()));
  }
}
