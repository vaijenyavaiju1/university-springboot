package com.example.university.model;


import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.*;
import com.example.university.model.*;


@Entity
@Table(name = "student")
public class Student {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private int studentId;


   @Column(name = "name")
   private String studentName;


   @Column(name = "email")
   private String email;


   @ManyToMany(mappedBy = "students")
   @JsonIgnoreProperties("students")
   private List<Course> courses;


   public Student() {
   }


   public Student(int studentId, String studentName, String email, List<Course> courses) {
      this.studentId = studentId;
      this.studentName = studentName;
      this.email = email;
      this.courses = courses;
   }


   public void setStudentId(int studentId) {
      this.studentId = studentId;
   }


   public int getStudentId() {
      return studentId;
   }


   public void setStudentName(String studentName) {
      this.studentName = studentName;
   }


   public String getStudentName() {
      return studentName;
   }


   public void setEmail(String email) {
      this.email = email;
   }


   public String getEmail() {
      return email;
   }


   public void setCourses(List<Course> courses) {
      this.courses = courses;
   }


   public List<Course> getCourses() {
      return courses;
   }


}