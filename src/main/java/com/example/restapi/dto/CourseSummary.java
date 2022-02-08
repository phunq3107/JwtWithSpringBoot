package com.example.restapi.dto;

import lombok.Data;

/**
 * @author phunq3107
 * @since 2/3/2022
 */
@Data
public class CourseSummary {

  private String courseId;
  private String courseName;
  private String teacherName;
  private Integer noStudent;
  private Long price;

  public CourseSummary(String courseId, String courseName, String teacherName,
      Integer noStudent, Long price) {
    this.courseId = courseId;
    this.courseName = courseName;
    this.teacherName = teacherName;
    this.noStudent = noStudent;
    this.price = price;
  }
}
