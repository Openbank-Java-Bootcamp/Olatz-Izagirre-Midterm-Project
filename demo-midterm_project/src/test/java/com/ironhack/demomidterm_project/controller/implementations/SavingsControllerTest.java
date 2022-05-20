package com.ironhack.demomidterm_project.controller.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demomidterm_project.DTO.CreditCardDTO;
import com.ironhack.demomidterm_project.DTO.SavingsDTO;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.model.Admin;
import com.ironhack.demomidterm_project.model.Role;
import com.ironhack.demomidterm_project.model.Savings;
import com.ironhack.demomidterm_project.repository.AccountHolderRepository;
import com.ironhack.demomidterm_project.repository.AccountRepository;
import com.ironhack.demomidterm_project.repository.AdminRepository;
import com.ironhack.demomidterm_project.repository.SavingsRepository;
import com.ironhack.demomidterm_project.service.implementation.AccountHolderService;
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
class SavingsControllerTest {
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
    private SavingsRepository savingsRepository;

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
        SavingsDTO savingsDTO = new SavingsDTO(new Money(BigDecimal.valueOf(2000L)),5L,"Pipipi");
        String body = objectMapper.writeValueAsString(savingsDTO);
        MvcResult mvcResult = mockMvc.perform(post("/api/accounts/savings").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("2000"));
        assertEquals(BigDecimal.valueOf(1000L).setScale(2),savingsRepository.findById(1L).get().getMinimumBalance().getAmount());
        assertEquals(BigDecimal.valueOf(0.0025).setScale(4),savingsRepository.findById(1L).get().getInterestRate());
    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456",roles = "ADMIN")
    void createAccount_ValidChosen_Created() throws Exception {
        SavingsDTO savingsDTO = new SavingsDTO(new Money(BigDecimal.valueOf(2000L)),5L,4L,new Money(BigDecimal.valueOf(200)),BigDecimal.valueOf(0.4),"Pipipi");
        String body = objectMapper.writeValueAsString(savingsDTO);
        MvcResult mvcResult = mockMvc.perform(post("/api/accounts/savings").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("2000"));
        assertEquals(BigDecimal.valueOf(200).setScale(2),savingsRepository.findById(1L).get().getMinimumBalance().getAmount());
        assertEquals(BigDecimal.valueOf(0.4).setScale(4),savingsRepository.findById(1L).get().getInterestRate());
        assertEquals("Olatz",savingsRepository.findById(1L).get().getSecondaryOwner().getName());
    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456",roles = "ADMIN")
    void createAccount_invalidPrimary_NotFound() throws Exception {
        SavingsDTO savingsDTO = new SavingsDTO(new Money(BigDecimal.valueOf(2000L)),7L,"Pipipi");
        String body = objectMapper.writeValueAsString(savingsDTO);
        MvcResult mvcResult = mockMvc.perform(post("/api/accounts/savings").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456",roles = "ADMIN")
    void createAccount_invalidSecondary_NotFound() throws Exception {
        SavingsDTO savingsDTO = new SavingsDTO(new Money(BigDecimal.valueOf(2000L)),5L,7L,"Pipipi");
        String body = objectMapper.writeValueAsString(savingsDTO);
        MvcResult mvcResult = mockMvc.perform(post("/api/accounts/savings").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456",roles = "ADMIN")
    void createAccount_balanceLessThanMinimumBalance_UnprocessableEntity() throws Exception {
        SavingsDTO savingsDTO = new SavingsDTO(new Money(BigDecimal.valueOf(500L)),5L,"Pipipi");
        String body = objectMapper.writeValueAsString(savingsDTO);
        MvcResult mvcResult = mockMvc.perform(post("/api/accounts/savings").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnprocessableEntity()).andReturn();
    }
}