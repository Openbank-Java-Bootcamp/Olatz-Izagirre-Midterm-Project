package com.ironhack.demomidterm_project.repository;

import com.ironhack.demomidterm_project.model.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHolderRepository extends JpaRepository<AccountHolder,Long> {
}
