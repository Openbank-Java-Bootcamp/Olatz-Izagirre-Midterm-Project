package com.ironhack.demomidterm_project.controller.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demomidterm_project.DTO.UserPasswordOnlyDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AdminService adminService;
    @Autowired
    private AccountHolderService accountHolderService;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

        accountHolderService.createAccountHolder(new AccountHolder("Olatz","Oli", Date.valueOf("1984-03-02"),new Address("Arene","Getxo",48991)));
        accountHolderService.createAccountHolder(new AccountHolder("Iker","Iki", Date.valueOf("1999-04-09"),new Address("Arene","Getxo",48991)));
        accountHolderService.createAccountHolder(new AccountHolder("Ama","Amatxu", Date.valueOf("2008-03-02"),new Address("Arene","Getxo",48991)));
    }

    @AfterEach
    void tearDown() {
        adminRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456",roles = "ADMIN")
    void deleteUser_Valid_NoContent() throws Exception {
        mockMvc.perform(delete("/api/users/1")).andExpect(status().isNoContent());
    }
    @Test
    @WithMockUser(username = "Aiko", password = "123456",roles = "ADMIN")
    void deleteUser_InvalidId_NotFound() throws Exception {
        mockMvc.perform(delete("/api/users/7")).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456",roles = "ADMIN")
    void getUsers_Valid_AllUsers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/users")).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Cas"));

    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456",roles = "ADMIN")
    void getUser_InvalidId_NotFound() throws Exception {
        mockMvc.perform(get("/api/users/7")).andExpect(status().isNotFound());
    }
    @Test
    @WithMockUser(username = "Aiko", password = "123456",roles = "ADMIN")
    void getUser_Valid_User() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/users/4")).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Aiko"));
    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456",roles = "ADMIN")
    void passwordChange_invalidUserLoggedIn_NotFound()throws Exception{
        Principal principal = () -> "Aiko";
        UserPasswordOnlyDTO password = new UserPasswordOnlyDTO("pipi");
        String body = objectMapper.writeValueAsString(password);
        mockMvc.perform(patch("/api/users/Jonas").principal(principal)
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456",roles = "ADMIN")
    void passwordChange_Valid_NoContent()throws Exception{
        Principal principal = () -> "Aiko";
        UserPasswordOnlyDTO password = new UserPasswordOnlyDTO("pipi");
        String body = objectMapper.writeValueAsString(password);
        MvcResult mvcResult = mockMvc.perform(patch("/api/users/Aiko").principal(principal)
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andReturn();
        assertTrue(passwordEncoder.matches("pipi",userRepository.findByUsername("Aiko").getPassword()));
    }
}