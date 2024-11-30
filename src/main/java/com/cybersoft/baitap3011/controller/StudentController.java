package com.cybersoft.baitap3011.controller;

import com.cybersoft.baitap3011.entity.Course;
import com.cybersoft.baitap3011.entity.Registration;
import com.cybersoft.baitap3011.entity.Student;
import com.cybersoft.baitap3011.repository.CourseRepo;
import com.cybersoft.baitap3011.repository.RegistrationRepo;
import com.cybersoft.baitap3011.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentRepo studentRepo;
    private RegistrationRepo registrationRepo;
    private CourseRepo courseRepo;

    @Autowired
    public StudentController(StudentRepo studentRepo, RegistrationRepo registrationRepo, CourseRepo courseRepo){
        this.studentRepo = studentRepo;
        this.registrationRepo = registrationRepo;
        this.courseRepo = courseRepo;
    }

    @GetMapping
    public ResponseEntity<?> getStudents(){
        return ResponseEntity.ok(studentRepo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable int id){
        return ResponseEntity.ok(studentRepo.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student student){
        return ResponseEntity.ok(studentRepo.save(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable int id, @RequestParam String name, @RequestParam String email, @RequestParam int age){
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setEmail(email);
        student.setAge(age);
        return ResponseEntity.ok(studentRepo.save(student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable int id){
        studentRepo.deleteById(id);
        return ResponseEntity.ok(studentRepo.findAll());
    }

    @GetMapping("/search?name={keyword}")
    public ResponseEntity<?> getStudentByKeywordName(@PathVariable String keyword){
        return ResponseEntity.ok(studentRepo.getStudentsByNameContainsIgnoreCase(keyword));
    }

    @PostMapping("/{studentId}/courses")
    public ResponseEntity<?> addCourseForStudent(@PathVariable int studentId, @RequestBody List<Course> courses){
        courses.stream().map(item ->{
            Registration registration = new Registration();
            registration.setStudent(studentRepo.getReferenceById(studentId));
            registration.setCourse(item);
            return registrationRepo.save(registration);
        });
        return ResponseEntity.ok(findCourseByStudentId(studentId));
    }

    @GetMapping("/{studentId}/coures")
    public ResponseEntity<?> findCourseByStudentId(@PathVariable int studentId){
        List<Course> courses = studentRepo.findRegistrationByStudentId(studentId).stream().map(Registration::getCourse).toList();
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/{studentId}/coures/{courseId}")
    public ResponseEntity<?> addStudentToCourse(@PathVariable int studentId, @PathVariable int courseId){
        Registration registration = new Registration();
        registration.setStudent(studentRepo.getReferenceById(studentId));
        registration.setCourse(courseRepo.getReferenceById(courseId));
        registrationRepo.save(registration);
        List<Student> students = courseRepo.getReferenceById(courseId).getRegistrationList().stream().map(Registration::getStudent).toList();
        return ResponseEntity.ok(students);
    }

    @DeleteMapping("/{studentId}/coures/{courseId}")
    public ResponseEntity<?> deleteStudentFromCourse(@PathVariable int studentId, @PathVariable int courseId){
        Student student = studentRepo.getReferenceById(studentId);
        Course course = courseRepo.getReferenceById(courseId);
        registrationRepo.deleteByCourseAndStudent(course, student);
        List<Student> students = courseRepo.getReferenceById(courseId).getRegistrationList().stream().map(Registration::getStudent).toList();
        return ResponseEntity.ok(students);
    }
}
