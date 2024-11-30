package com.cybersoft.baitap3011.repository;

import com.cybersoft.baitap3011.entity.Course;
import com.cybersoft.baitap3011.entity.Registration;
import com.cybersoft.baitap3011.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
    List<Student> getStudentsByNameContainsIgnoreCase(String keyword);

    @Query(value = "SELECT s.registrations FROM student s WHERE  s.id = ?", nativeQuery = true)
    List<Registration> findRegistrationByStudentId(int id);



}
