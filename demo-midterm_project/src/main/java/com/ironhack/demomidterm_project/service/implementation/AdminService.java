package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.model.Admin;
import com.ironhack.demomidterm_project.model.ThirdParty;
import com.ironhack.demomidterm_project.repository.*;
import com.ironhack.demomidterm_project.service.interfaces.AdminServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@Service
@Slf4j
public class AdminService implements AdminServiceInterface {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    public Admin createAdmin (Admin admin){
        if(userRepository.findByUsername(admin.getUsername())!= null){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Username already exists");
        }
        if (admin.getId() != null){
            Optional<Admin> optionalAdmin = adminRepository.findById(admin.getId());
            if(optionalAdmin.isPresent()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Admin already exists");
        }
        admin.setRole(roleRepository.findByName("ADMIN"));
        admin.setPassword(passwordEncoder.encode("123456"));
        return adminRepository.save(admin);
    }




}
