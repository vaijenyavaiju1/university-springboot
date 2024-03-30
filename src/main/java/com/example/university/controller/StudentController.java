package com.example.university.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


import com.example.university.model.*;
import com.example.university.service.*;


@RestController
public class StudentController {
    @Autowired
    public StudentJpaService studentService;


    @GetMapping("students")
    public ArrayList<Student> getStudents() {
        return studentService.getStudents();
    }


    @GetMapping("/students/{studentId}")
    public Student getStudentById(@PathVariable("studentId") int studentId) {
        return studentService.getStudentById(studentId);
    }


    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }


    @PutMapping("/students/{studentId}")
    public Student updateStudent(@PathVariable("studentId") int studentId, @RequestBody Student student) {
        return studentService.updateStudent(studentId, student);
    }


    @DeleteMapping("/students/{studentId}")
    public void deleteStudent(@PathVariable("studentId") int studentId) {
        studentService.deleteStudent(studentId);
    }


    @GetMapping("/students/{studentId}/courses")
    public List<Course> getCoursesOfStudent(@PathVariable("studentId") int studentId) {
        return studentService.getCoursesOfStudent(studentId);
    }
}