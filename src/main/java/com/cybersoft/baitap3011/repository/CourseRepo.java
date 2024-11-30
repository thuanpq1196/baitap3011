package com.cybersoft.baitap3011.repository;

import com.cybersoft.baitap3011.entity.Course;
import com.cybersoft.baitap3011.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
    List<Course> findCourseByDurationGreaterThan(double hours);

    int countAllBy();

    @Query(value = "SELECT c.registrationList FROM course c WHERE c.id = ?", nativeQuery = true)
    List<Registration> getRegistrationsByCourse(int id);
}
