package com.example.university.repository;

import java.util.*;
import com.example.university.model.*;

public interface CourseRepository {
    ArrayList<Course> getCourses();

    Course getCourseById(int courseId);

    Course addCourse(Course course);

    Course updateCourse(int courseId, Course course);

    void deleteCourse(int courseId);

    Professor getProfessorOfCourse(int courseId);

    List<Student> getStudentsOfCourse(int courseId);
}