package com.example.restapi.service;

import com.example.restapi.dao.CourseDAO;
import com.example.restapi.dao.StudentCourseDAO;
import com.example.restapi.dao.StudentDAO;
import com.example.restapi.dao.TeacherDAO;
import com.example.restapi.dao.UserDAO;
import com.example.restapi.dto.StudentInfo;
import com.example.restapi.dto.TeacherInfo;
import com.example.restapi.entity.Course;
import com.example.restapi.entity.Student;
import com.example.restapi.entity.StudentCourse;
import com.example.restapi.entity.StudentCourse.Id;
import com.example.restapi.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author phunq3107
 * @since 2/5/2022
 */
@Service
@AllArgsConstructor
@Slf4j
public class AppService {

  private final UserDAO userDAO;
  private final  UserService userService;
  private final TeacherDAO teacherDAO;
  private final StudentDAO studentDAO;
  private final CourseDAO courseDAO;
  private final StudentCourseDAO studentCourseDAO;


  public void initial() {
    addTeacher("Teacher 1");
    addTeacher("Teacher 2");
    addTeacher("Teacher 3");

    addStudent("Student 1");
    addStudent("Student 2");
    addStudent("Student 3");
    addStudent("Student 4");

    addCourse("CO2013", "Database", 200_000L, 1L);
    addCourse("CO2015", "Operating System", 100_000L, 2L);
    addCourse("CO2017", "DSA", 200_000L, 2L);

    addStudentToCourse(4L, "CO2013");
    addStudentToCourse(5L, "CO2013");
    addStudentToCourse(6L, "CO2013");
    addStudentToCourse(6L, "CO2015");
    addStudentToCourse(7L, "CO2017");

    updateStudentMark(4L, "CO2013", 10);
    updateStudentMark(5L, "CO2013", 7);
    updateStudentMark(6L, "CO2013", 8);
    updateStudentMark(6L, "CO2015", 9);
    updateStudentMark(7L, "CO2017", 8);

  }

  public void main() {

  }


  public Teacher addTeacher(String teacherName) {
    Teacher teacher = new Teacher();
    teacher.setName(teacherName);
    teacher.setUsername(teacherName);
    teacher.setPassword("t");
    teacher.setRole("ROLE_TEACHER");
    return (Teacher) userService.createUser(teacher);
  }

  public Student addStudent(String studentName) {
    Student student = new Student();
    student.setName(studentName);
    student.setUsername(studentName);
    student.setPassword("s");
    student.setRole("ROLE_STUDENT");
    return (Student) userService.createUser(student);
  }

  public Course addCourse(String courseId, String courseName, Long coursePrice, Long teacherId) {
    Teacher teacher = teacherDAO.findById(teacherId);
    if (teacher == null) {
      log.error(String.format("Teacher with %d id not exists", teacherId));
      return null;
    }
    Course course = new Course();
    course.setId(courseId);
    course.setName(courseName);
    course.setPrice(coursePrice);
    course.setTeacher(teacher);
    return courseDAO.makePersistence(course);
  }


  public void addStudentToCourse(Long studentId, String courseId) {
    Student student = studentDAO.findById(studentId);
    if (student == null) {
      log.error(String.format("Student with %d id not exists", studentId));
      return;
    }
    Course course = courseDAO.findById(courseId);
    if (course == null) {
      log.error(String.format("Course with %s id not exists", courseId));
      return;
    }
    StudentCourse studentCourse = new StudentCourse(student, course);
    studentCourse.setMark(-1);
    studentCourseDAO.makePersistence(studentCourse);
  }

  public void updateStudentMark(Long studentId, String courseId, Integer mark) {
    StudentCourse studentCourse = studentCourseDAO.findById(new Id(studentId, courseId));
    if (studentCourse == null) {
      log.error(String.format("Tuple (%d, %s) is not exists", studentId, courseId));
      return;
    }
    studentCourse.setMark(mark);
    studentCourseDAO.makePersistence(studentCourse);
  }

  public StudentInfo getStudentInfo(Long studentId) {
    return studentDAO.getStudentInfo(studentId);
  }

  public TeacherInfo getTeacherInfo(Long teacherId) {
    return teacherDAO.getTeacherInfo(teacherId);
  }


}
