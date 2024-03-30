package com.example.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

import com.example.university.repository.*;
import com.example.university.model.*;

@Service
public class CourseJpaService implements CourseRepository {
    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Autowired
    private ProfessorJpaRepository professorJpaRepository;

    @Override
    public ArrayList<Course> getCourses() {
        List<Course> courselist = courseJpaRepository.findAll();
        ArrayList<Course> courses = new ArrayList<>(courselist);
        return courses;
    }

    @Override
    public Course getCourseById(int courseId) {
        try {
            Course course = courseJpaRepository.findById(courseId).get();
            return course;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Course addCourse(Course course) {
        try {
            Professor professor = course.getProfessor();

            int professorId = professor.getProfessorId();

            Professor existingProfessor = professorJpaRepository.findById(professorId).get();

            course.setProfessor(existingProfessor);

            List<Student> students = course.getStudents();

            List<Integer> studentIds = new ArrayList<>();

            for (Student student : students) {
                studentIds.add(student.getStudentId());
            }

            List<Student> existingStudents = studentJpaRepository.findAllById(studentIds);

            course.setStudents(existingStudents);

            courseJpaRepository.save(course);

            return course;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong ProfessorId");
        }
    }

    @Override
    public Course updateCourse(int courseId, Course course) {
        try {
            Course newCourse = courseJpaRepository.findById(courseId).get();
            if (course.getCourseName() != null) {
                newCourse.setCourseName(course.getCourseName());
            }
            if (course.getCredits() != 0) {
                newCourse.setCredits(course.getCredits());

            }
            if (course.getProfessor() != null) {
                Professor professor = course.getProfessor();
                int professorId = professor.getProfessorId();
                Professor existingProfessor = professorJpaRepository.findById(professorId).get();
                newCourse.setProfessor(existingProfessor);
            }
            if (course.getStudents() != null) {
                List<Student> students = course.getStudents();
                List<Integer> studentIds = new ArrayList<>();
                for (Student student : students) {
                    studentIds.add(student.getStudentId());
                }
                List<Student> existingStudents = studentJpaRepository.findAllById(studentIds);
                newCourse.setStudents(existingStudents);
            }

            courseJpaRepository.save(newCourse);
            return newCourse;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override

    public void deleteCourse(int courseId) {
        try {
            courseJpaRepository.deleteById(courseId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);

    }

    @Override
    public Professor getProfessorOfCourse(int courseId) {
        try {
            Course course = courseJpaRepository.findById(courseId).get();
            Professor professor = course.getProfessor();
            return professor;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Student> getStudentsOfCourse(int courseId) {
        try {
            Course course = courseJpaRepository.findById(courseId).get();
            return course.getStudents();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}