package com.ironhack.demomidterm_project.repository;

import com.ironhack.demomidterm_project.model.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount,Long> {
}
