package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.model.Role;
import com.ironhack.demomidterm_project.repository.RoleRepository;
import com.ironhack.demomidterm_project.service.interfaces.RoleServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleService implements RoleServiceInterface {
    @Autowired
    private RoleRepository roleRepository;

    public Role saveRole(Role role) {
        log.info("Saving a new role {} to the database",role.getName());
        return roleRepository.save(role);
    }


}
