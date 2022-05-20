package com.ironhack.demomidterm_project.controller.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.model.Admin;
import com.ironhack.demomidterm_project.model.Role;
import com.ironhack.demomidterm_project.repository.AccountHolderRepository;
import com.ironhack.demomidterm_project.repository.AdminRepository;
import com.ironhack.demomidterm_project.repository.UserRepository;
import com.ironhack.demomidterm_project.service.implementation.AccountHolderService;
import com.ironhack.demomidterm_project.service.implementation.AdminService;
import com.ironhack.demomidterm_project.service.implementation.RoleService;
import com.ironhack.demomidterm_project.utils.Address;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
class AdminControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;


    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        roleService.saveRole(new Role("ADMIN"));
        roleService.saveRole(new Role("ACCOUNT_HOLDER"));

        adminService.createAdmin(new Admin("Aiko Tanaka", "Aiko"));
        adminService.createAdmin(new Admin("Jonas Schmidt","Jonas"));
        adminService.createAdmin(new Admin("Cas Van Dijk","Cas"));
    }

    @AfterEach
    void tearDown() {
        adminRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456",roles = "ADMIN")
    void createAdmin_Valid_Created () throws Exception {
        Admin admin = new Admin("Pepa","Pepi");
        String body = objectMapper.writeValueAsString(admin);
        MvcResult mvcResult = mockMvc.perform(post("/api/users/admins").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Pepi"));
    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456",roles = "ADMIN")
    void createAdmin_existingUsername_unprocessableEntity () throws Exception {
        Admin admin = new Admin("Pepa","Cas");
        String body = objectMapper.writeValueAsString(admin);
        MvcResult mvcResult = mockMvc.perform(post("/api/users/admins").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnprocessableEntity()).andReturn();
    }
}