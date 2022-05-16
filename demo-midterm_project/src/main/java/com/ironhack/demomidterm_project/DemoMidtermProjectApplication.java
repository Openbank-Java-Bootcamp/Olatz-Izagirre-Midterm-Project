package com.ironhack.demomidterm_project;

import com.ironhack.demomidterm_project.model.Admin;
import com.ironhack.demomidterm_project.model.Role;
import com.ironhack.demomidterm_project.service.implementation.AccountHolderService;
import com.ironhack.demomidterm_project.service.implementation.AdminService;
import com.ironhack.demomidterm_project.service.implementation.RoleService;
import com.ironhack.demomidterm_project.service.implementation.ThirdPartyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class DemoMidtermProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMidtermProjectApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(AdminService adminService, AccountHolderService accountHolderService, ThirdPartyService thirdPartyService, RoleService roleService) {
        return args -> {
            roleService.saveRole(new Role("ADMIN"));
            roleService.saveRole(new Role("ACCOUNT_HOLDER"));
            roleService.saveRole(new Role("THIRD_PARTY"));

            adminService.createAdmin(new Admin("Olatz","Oli"));

        };
    }
}
