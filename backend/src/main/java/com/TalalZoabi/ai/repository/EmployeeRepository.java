package com.TalalZoabi.ai.repository;

import com.TalalZoabi.ai.model.Employee;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE e.email = ?1")
    Optional<Employee> findByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM Employee e WHERE e.email = ?1")
    Boolean existsByEmail(String email);
} 
