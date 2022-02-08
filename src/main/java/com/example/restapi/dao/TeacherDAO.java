package com.example.restapi.dao;

import com.example.restapi.dto.TeacherInfo;
import com.example.restapi.entity.Teacher;

/**
 * @author phunq3107
 * @since 2/5/2022
 */
public interface TeacherDAO extends GenericDAO<Teacher, Long> {

  public TeacherInfo getTeacherInfo(Long teacherId);
}
