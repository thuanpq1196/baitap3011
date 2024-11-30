package com.cybersoft.baitap3011.controller;

import com.cybersoft.baitap3011.entity.Registration;
import com.cybersoft.baitap3011.entity.Student;
import com.cybersoft.baitap3011.repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private CourseRepo courseRepo;
    @Autowired
    public CourseController(CourseRepo courseRepo){
        this.courseRepo = courseRepo;
    }

    @GetMapping("?durationGreaterThan={hours}")
    public ResponseEntity<?> getCourseHaveDurationGreaterThan(@PathVariable double hours){
        return ResponseEntity.ok(courseRepo.findCourseByDurationGreaterThan(hours));
    }

    @GetMapping("/count")
    public ResponseEntity<?> countAll(){
        return ResponseEntity.ok(courseRepo.countAllBy());
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<?> getStudentListByCourse(@PathVariable int courseId){
        List<Student> students = courseRepo.getRegistrationsByCourse(courseId).stream().map(Registration::getStudent).toList();
        return ResponseEntity.ok(students);
    }

}
