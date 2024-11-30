package com.cybersoft.baitap3011.repository;

import com.cybersoft.baitap3011.entity.Course;
import com.cybersoft.baitap3011.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepo extends JpaRepository<Registration, Integer> {
}
