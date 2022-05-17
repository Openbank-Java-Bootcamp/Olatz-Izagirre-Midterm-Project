package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.model.ThirdParty;
import com.ironhack.demomidterm_project.repository.RoleRepository;
import com.ironhack.demomidterm_project.repository.ThirdPartyRepository;
import com.ironhack.demomidterm_project.service.interfaces.ThirdPartyServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@Service
@Slf4j
public class ThirdPartyService implements ThirdPartyServiceInterface {

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    public ThirdParty createThirdParty (ThirdParty thirdParty){
        if (thirdParty.getId() != null){
            Optional<ThirdParty> optionalThirdParty = thirdPartyRepository.findById(thirdParty.getId());
            if(optionalThirdParty.isPresent()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Third Party already exists");
        }
        thirdParty.setRole(roleRepository.findByName("THIRD_PARTY"));
        thirdParty.setPassword(passwordEncoder.encode("123456"));
        return thirdPartyRepository.save(thirdParty);
    }
}
