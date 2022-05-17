package com.ironhack.demomidterm_project.repository;

import com.ironhack.demomidterm_project.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long> {
    @Query(value = "SELECT *, 0 AS clazz_ FROM account WHERE account.primary_account_holder_id = :id OR account.secondary_account_holder_id = :id",nativeQuery = true)
    List<Account> findByAccountOwnerId(@Param("id") Long id);
}
