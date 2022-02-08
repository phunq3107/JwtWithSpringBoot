package com.example.restapi.dao;

import com.example.restapi.dto.StudentInfo;
import com.example.restapi.entity.Student;

/**
 * @author phunq3107
 * @since 2/5/2022
 */
public interface StudentDAO  extends  GenericDAO<Student, Long> {
  public StudentInfo getStudentInfo(Long studentId);
}
