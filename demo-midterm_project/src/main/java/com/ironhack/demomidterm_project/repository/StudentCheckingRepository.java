package com.ironhack.demomidterm_project.repository;

import com.ironhack.demomidterm_project.model.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCheckingRepository extends JpaRepository<StudentChecking,Long> {
}
