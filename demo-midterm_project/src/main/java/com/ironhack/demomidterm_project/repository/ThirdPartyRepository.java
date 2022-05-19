package com.ironhack.demomidterm_project.repository;

import com.ironhack.demomidterm_project.model.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThirdPartyRepository extends JpaRepository<ThirdParty,Long> {
    ThirdParty findByHashedKey(String hashedKey);
}
