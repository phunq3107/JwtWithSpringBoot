package com.example.restapi.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author phunq3107
 * @since 2/3/2022
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

  @Id
  private String id;

  private String name;

  private Long price;

  @ManyToOne
  private Teacher teacher;

  @OneToMany(mappedBy = "course")
  private List<StudentCourse> studentCourses = new ArrayList<>();

  @Override
  public String toString() {
    return "Course{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", price=" + price +
        ", teacher=" + teacher.getName() +
        '}';
  }
}
