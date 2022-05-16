package com.ironhack.demomidterm_project.repository;

import com.ironhack.demomidterm_project.model.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsRepository extends JpaRepository<Savings,Long> {
}
