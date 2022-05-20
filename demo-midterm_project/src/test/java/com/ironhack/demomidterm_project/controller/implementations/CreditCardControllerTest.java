package com.ironhack.demomidterm_project.controller.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demomidterm_project.DTO.CreditCardDTO;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.model.Admin;
import com.ironhack.demomidterm_project.model.Role;
import com.ironhack.demomidterm_project.repository.AccountHolderRepository;
import com.ironhack.demomidterm_project.repository.AccountRepository;
import com.ironhack.demomidterm_project.repository.AdminRepository;
import com.ironhack.demomidterm_project.repository.CreditCardRepository;
import com.ironhack.demomidterm_project.service.implementation.AccountHolderService;
import com.ironhack.demomidterm_project.service.implementation.AccountService;
import com.ironhack.demomidterm_project.service.implementation.AdminService;
import com.ironhack.demomidterm_project.service.implementation.RoleService;
import com.ironhack.demomidterm_project.utils.Address;
import com.ironhack.demomidterm_project.utils.Money;
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

import java.math.BigDecimal;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CreditCardControllerTest {
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
    private AccountRepository accountRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;

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
        accountRepository.deleteAll();
        adminRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456",roles = "ADMIN")
    void createAccount_ValidDefault_Created() throws Exception {
        CreditCardDTO creditCardDTO = new CreditCardDTO(new Money(BigDecimal.valueOf(500L)),5L);
        String body = objectMapper.writeValueAsString(creditCardDTO);
        MvcResult mvcResult = mockMvc.perform(post("/api/accounts/creditCards").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("500"));
        assertEquals(BigDecimal.valueOf(0.2).setScale(2),creditCardRepository.findById(1l).get().getInterestRate());
        assertEquals(BigDecimal.valueOf(100).setScale(2),creditCardRepository.findById(1l).get().getCreditLimit().getAmount());
    }
    @Test
    @WithMockUser(username = "Aiko", password = "123456",roles = "ADMIN")
    void createAccount_ValidChosen_Created() throws Exception {
        CreditCardDTO creditCardDTO = new CreditCardDTO(new Money(BigDecimal.valueOf(500L)),5L,4L,new Money(BigDecimal.valueOf(200)),BigDecimal.valueOf(0.15));
        String body = objectMapper.writeValueAsString(creditCardDTO);
        MvcResult mvcResult = mockMvc.perform(post("/api/accounts/creditCards").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("500"));
        assertEquals(BigDecimal.valueOf(0.15).setScale(2),creditCardRepository.findById(1l).get().getInterestRate());
        assertEquals(BigDecimal.valueOf(200).setScale(2),creditCardRepository.findById(1l).get().getCreditLimit().getAmount());
        assertEquals("Olatz",creditCardRepository.findById(1l).get().getSecondaryOwner().getName());
    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456",roles = "ADMIN")
    void createAccount_InvalidPrimary_NotFound() throws Exception {
        CreditCardDTO creditCardDTO = new CreditCardDTO(new Money(BigDecimal.valueOf(500L)),1L);
        String body = objectMapper.writeValueAsString(creditCardDTO);
        mockMvc.perform(post("/api/accounts/creditCards").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
    }
    @Test
    @WithMockUser(username = "Aiko", password = "123456",roles = "ADMIN")
    void createAccount_InvalidSecondary_NotFound() throws Exception {
        CreditCardDTO creditCardDTO = new CreditCardDTO(new Money(BigDecimal.valueOf(500L)),5L,1L);
        String body = objectMapper.writeValueAsString(creditCardDTO);
        mockMvc.perform(post("/api/accounts/creditCards").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
    }
}