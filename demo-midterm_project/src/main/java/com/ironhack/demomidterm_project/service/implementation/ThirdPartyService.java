package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.model.ThirdParty;
import com.ironhack.demomidterm_project.model.User;
import com.ironhack.demomidterm_project.repository.RoleRepository;
import com.ironhack.demomidterm_project.repository.ThirdPartyRepository;
import com.ironhack.demomidterm_project.repository.UserRepository;
import com.ironhack.demomidterm_project.service.interfaces.ThirdPartyServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
@Service
@Slf4j
public class ThirdPartyService implements ThirdPartyServiceInterface {

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;


    public ThirdParty createThirdParty (ThirdParty thirdParty){
        if (thirdParty.getId() != null){
            Optional<ThirdParty> optionalThirdParty = thirdPartyRepository.findById(thirdParty.getId());
            if(optionalThirdParty.isPresent()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Third Party already exists");
        }
        return thirdPartyRepository.save(thirdParty);
    }

    public List<ThirdParty> getThirdParties (){
        return thirdPartyRepository.findAll();
    }

    public void deleteUser (Long id){
        thirdPartyRepository.deleteById(id);
    }
}
