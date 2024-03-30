package com.example.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import com.example.university.model.*;
import com.example.university.repository.*;

@Service
public class StudentJpaService implements StudentRepository {
    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Override
    public ArrayList<Student> getStudents() {
        List<Student> studentlist = studentJpaRepository.findAll();
        ArrayList<Student> students = new ArrayList<>(studentlist);
        return students;
    }

    @Override

    public Student getStudentById(int studentId) {
        try {
            Student student = studentJpaRepository.findById(studentId).get();
            return student;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Student addStudent(Student student) {
        List<Integer> courseIds = new ArrayList<>();
        for (Course course : student.getCourses()) {
            courseIds.add(course.getCourseId());
        }
        List<Course> courses = courseJpaRepository.findAllById(courseIds);
        if (courses.size() != courseIds.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        student.setCourses(courses);
        for (Course course : courses) {
            course.getStudents().add(student);
        }
        studentJpaRepository.save(student);
        courseJpaRepository.saveAll(courses);
        return student;
    }

    @Override
    public Student updateStudent(int studentId, Student student) {
        try {
            Student newStudent = studentJpaRepository.findById(studentId).get();
            if (student.getStudentName() != null) {
                newStudent.setStudentName(student.getStudentName());
            }
            if (student.getEmail() != null) {
                newStudent.setEmail(student.getEmail());
            }
            if (student.getCourses() != null) {
                List<Course> existingCourses = newStudent.getCourses();
                for (Course course : existingCourses) {
                    course.getStudents().remove(newStudent);
                }
                courseJpaRepository.saveAll(existingCourses);

                List<Integer> courseIds = new ArrayList<>();
                for (Course course : student.getCourses()) {
                    courseIds.add(course.getCourseId());
                }

                List<Course> courses = courseJpaRepository.findAllById(courseIds);
                if (courses.size() != courseIds.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                newStudent.setCourses(courses);
                for (Course course : courses) {
                    course.getStudents().add(newStudent);
                }
                courseJpaRepository.saveAll(courses);
            }
            studentJpaRepository.save(newStudent);
            return newStudent;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteStudent(int studentId) {
        try {
            Student student = studentJpaRepository.findById(studentId).get();
            List<Course> courses = student.getCourses();
            for (Course course : courses) {
                course.getStudents().remove(student);
            }
            courseJpaRepository.saveAll(courses);
            studentJpaRepository.deleteById(studentId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Course> getCoursesOfStudent(int studentId) {
        try {
            Student student = studentJpaRepository.findById(studentId).get();
            List<Course> courses = student.getCourses();
            return courses;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}