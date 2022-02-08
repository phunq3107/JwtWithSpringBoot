package com.example.restapi.resources;

import com.example.restapi.dto.StudentInfo;
import com.example.restapi.dto.TeacherInfo;
import com.example.restapi.service.AppService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author phunq3107
 * @since 2/7/2022
 */
@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class Resources {

  public final AppService service;

  @GetMapping(path = "/student/{studentId}")
  @PreAuthorize(value = "hasRole('STUDENT')")
  public ResponseEntity<StudentInfo> getStudentInfo(@PathVariable("studentId") Long studentId) {
    StudentInfo info = service.getStudentInfo(studentId);
    if (info == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(info);
    }
  }

  @GetMapping(path = "/teacher/{teacherId}")
  @PreAuthorize("hasRole('TEACHER')")
  public ResponseEntity<TeacherInfo> getTeacherInfo(@PathVariable("teacherId") Long teacherId) {
    TeacherInfo info = service.getTeacherInfo(teacherId);
    if (info == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(info);
    }
  }


}
