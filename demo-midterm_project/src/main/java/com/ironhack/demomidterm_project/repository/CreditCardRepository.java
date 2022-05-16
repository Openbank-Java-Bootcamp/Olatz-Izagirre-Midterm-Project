package com.ironhack.demomidterm_project.repository;

import com.ironhack.demomidterm_project.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard,Long> {
}
