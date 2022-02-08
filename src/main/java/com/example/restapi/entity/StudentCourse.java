package com.example.restapi.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author phunq3107
 * @since 2/5/2022
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourse {

  @Embeddable
  public static class Id implements Serializable {

    @Column(name = "STUDENT_ID")
    private Long studentId;

    @Column(name = "COURSE_ID")
    private String courseId;

    public Id() {
    }

    public Id(Long studentId, String courseId) {
      this.studentId = studentId;
      this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Id id = (Id) o;

      if (!studentId.equals(id.studentId)) {
        return false;
      }
      return courseId.equals(id.courseId);
    }

    @Override
    public int hashCode() {
      int result = studentId.hashCode();
      result = 31 * result + courseId.hashCode();
      return result;
    }
  }

  @EmbeddedId
  private Id id = new Id();

  @ManyToOne
  @JoinColumn(name = "STUDENT_ID", updatable = false, insertable = false)
  private Student student;

  @ManyToOne
  @JoinColumn(name = "COURSE_ID", updatable = false, insertable = false)
  private Course course;

  private Integer mark;

  public StudentCourse(Student student, Course course) {
    this.id.courseId = course.getId();
    this.id.studentId = student.getId();

    this.student = student;
    this.course = course;

//    student.getStudentCourses().add(this);
//    course.getStudentCourses().add(this);
  }
}
