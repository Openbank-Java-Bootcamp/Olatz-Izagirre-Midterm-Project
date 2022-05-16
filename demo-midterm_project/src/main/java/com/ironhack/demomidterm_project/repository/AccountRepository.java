package com.ironhack.demomidterm_project.repository;

import com.ironhack.demomidterm_project.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
