package com.example.restapi.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author phunq3107
 * @since 2/3/2022
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User{

//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private Long id;

  private String name;

  @OneToMany(mappedBy = "student")
  private List<StudentCourse> studentCourses = new ArrayList<>();

  @Override
  public String toString() {
    return "Student{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
