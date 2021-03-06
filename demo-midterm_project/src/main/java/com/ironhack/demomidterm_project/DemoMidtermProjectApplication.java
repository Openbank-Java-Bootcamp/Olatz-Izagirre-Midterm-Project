package com.ironhack.demomidterm_project;

import com.ironhack.demomidterm_project.model.Admin;
import com.ironhack.demomidterm_project.model.Role;
import com.ironhack.demomidterm_project.service.implementation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class DemoMidtermProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMidtermProjectApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public Environment environment;

    @Bean
    CommandLineRunner run(AdminService adminService, AccountHolderService accountHolderService, ThirdPartyService thirdPartyService, RoleService roleService, SavingsService savingsService, CreditCardService creditCardService) {
        return args -> {if(!Arrays.asList(environment.getActiveProfiles()).contains("test")) {
            roleService.saveRole(new Role("ADMIN"));
            roleService.saveRole(new Role("ACCOUNT_HOLDER"));


            adminService.createAdmin(new Admin("Olatz","Oli"));

            savingsService.savingsInterests();
            creditCardService.creditCardInterests();

        }};
    }
}
