package com.ironhack.demomidterm_project.controller.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demomidterm_project.DTO.*;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.model.Admin;
import com.ironhack.demomidterm_project.model.Role;
import com.ironhack.demomidterm_project.model.ThirdParty;
import com.ironhack.demomidterm_project.repository.AccountHolderRepository;
import com.ironhack.demomidterm_project.repository.AccountRepository;
import com.ironhack.demomidterm_project.repository.AdminRepository;
import com.ironhack.demomidterm_project.service.implementation.*;
import com.ironhack.demomidterm_project.utils.Address;
import com.ironhack.demomidterm_project.utils.Money;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class AccountControllerTest {
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
    private SavingsService savingsService;
    @Autowired
    private CheckingAccountService checkingAccountService;
    @Autowired
    private CreditCardService creditCardService;
    @Autowired
    private StudentCheckingService studentCheckingService;
    @Autowired
    private ThirdPartyService thirdPartyService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        roleService.saveRole(new Role("ADMIN"));
        roleService.saveRole(new Role("ACCOUNT_HOLDER"));

        adminService.createAdmin(new Admin("Aiko Tanaka", "Aiko"));
        adminService.createAdmin(new Admin("Jonas Schmidt", "Jonas"));
        adminService.createAdmin(new Admin("Cas Van Dijk", "Cas"));

        accountHolderService.createAccountHolder(new AccountHolder("Olatz", "Oli", Date.valueOf("1984-03-02"), new Address("Arene", "Getxo", 48991)));
        accountHolderService.createAccountHolder(new AccountHolder("Iker", "Iki", Date.valueOf("1999-04-09"), new Address("Arene", "Getxo", 48991)));
        accountHolderService.createAccountHolder(new AccountHolder("Ama", "Amatxu", Date.valueOf("2008-03-02"), new Address("Arene", "Getxo", 48991)));

        checkingAccountService.createAccount(new CheckingDTO(new Money(BigDecimal.valueOf(300)), 4L, "Pipipi"));
        studentCheckingService.createAccount(new CheckingDTO(new Money(BigDecimal.valueOf(300)), 5L, "Papapa"));
        creditCardService.createAccount(new CreditCardDTO(new Money(BigDecimal.valueOf(500)), 6L));
        savingsService.createAccount(new SavingsDTO(new Money(BigDecimal.valueOf(1500)), 4L, "Lilili"));

        thirdPartyService.createThirdParty(new ThirdParty("Loli", "Lalala"));


    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
        adminRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456", roles = "ADMIN")
    void deleteAccount_Valid_NoContent() throws Exception {
        mockMvc.perform(delete("/api/accounts/1")).andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456", roles = "ADMIN")
    void deleteAccount_invalidId_NotFound() throws Exception {
        mockMvc.perform(delete("/api/accounts/5")).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456", roles = "ADMIN")
    void getAccountBalance_Valid_AllUsers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/accounts/1/balance")).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("300"));

    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456", roles = "ADMIN")
    void getAccountBalance_invalidId_NotFount() throws Exception {
        mockMvc.perform(get("/api/accounts/5/balance")).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "Aiko", password = "123456", roles = "ADMIN")
    void updateAccountBalance_Valid_NoContent() throws Exception {
        BigDecimal newBalance = new BigDecimal(400);
        AccountBalanceOnlyDTO accountBalanceOnlyDTO = new AccountBalanceOnlyDTO(new Money(newBalance));
        String body = objectMapper.writeValueAsString(accountBalanceOnlyDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/api/accounts/1/balance")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andReturn();
        assertEquals(newBalance.setScale(2), accountRepository.findById(1L).get().getBalance().getAmount());
    }
    @Test
    @WithMockUser(username = "Aiko", password = "123456", roles = "ADMIN")
    void updateAccountBalance_invalidId_NotFound() throws Exception {
        BigDecimal newBalance = new BigDecimal(400);
        AccountBalanceOnlyDTO accountBalanceOnlyDTO = new AccountBalanceOnlyDTO(new Money(newBalance));
        String body = objectMapper.writeValueAsString(accountBalanceOnlyDTO);
        mockMvc.perform(patch("/api/accounts/5/balance")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }
    @Test
    @WithMockUser(username = "Aiko", password = "123456", roles = "ADMIN")
    void updateAccountBalance_Valid_PenaltyFeeApplicationChecking() throws Exception {
        BigDecimal newBalance = new BigDecimal(230);
        AccountBalanceOnlyDTO accountBalanceOnlyDTO = new AccountBalanceOnlyDTO(new Money(newBalance));
        String body = objectMapper.writeValueAsString(accountBalanceOnlyDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/api/accounts/1/balance")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andReturn();
        assertEquals(BigDecimal.valueOf(190).setScale(2), accountRepository.findById(1L).get().getBalance().getAmount());
    }
    @Test
    @WithMockUser(username = "Aiko", password = "123456", roles = "ADMIN")
    void updateAccountBalance_Valid_PenaltyFeeApplicationSavings() throws Exception {
        BigDecimal newBalance = new BigDecimal(500);
        AccountBalanceOnlyDTO accountBalanceOnlyDTO = new AccountBalanceOnlyDTO(new Money(newBalance));
        String body = objectMapper.writeValueAsString(accountBalanceOnlyDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/api/accounts/4/balance")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andReturn();
        assertEquals(BigDecimal.valueOf(460).setScale(2), accountRepository.findById(4L).get().getBalance().getAmount());
    }

    @Test
    void sendMoney_Valid_NoContent() throws Exception {
        ThirdPartyTransferDTO thirdPartyTransferDTO = new ThirdPartyTransferDTO(new Money(BigDecimal.valueOf(50)), 1L, "Pipipi", "Lalala");
        String body = objectMapper.writeValueAsString(thirdPartyTransferDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/api/accounts/sendMoney")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andReturn();
        assertEquals(BigDecimal.valueOf(350).setScale(2), accountRepository.findById(1L).get().getBalance().getAmount());
    }

    @Test
    void receiveMoney_Valid_NoContent() throws Exception {
        ThirdPartyTransferDTO thirdPartyTransferDTO = new ThirdPartyTransferDTO(new Money(BigDecimal.valueOf(50)), 1L, "Pipipi", "Lalala");
        String body = objectMapper.writeValueAsString(thirdPartyTransferDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/api/accounts/receiveMoney")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andReturn();
        assertEquals(BigDecimal.valueOf(250).setScale(2), accountRepository.findById(1L).get().getBalance().getAmount());
    }

    @Test
    @WithMockUser(username = "Oli", password = "123456", roles = "ACCOUNT_HOLDER")
    void getUsersAccount_Valid_AllAccounts() throws Exception {
        Principal principal = () -> "Oli";
        MvcResult mvcResult = mockMvc.perform(get("/api/accounts/Oli").principal(principal)).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1500"));

    }
    @Test
    @WithMockUser(username = "Oli", password = "123456", roles = "ACCOUNT_HOLDER")
    void getUsersAccount_invalidUserLogged_Forbidden() throws Exception {
        Principal principal = () -> "Oli";
        mockMvc.perform(get("/api/accounts/Iki").principal(principal)).andExpect(status().isForbidden());

    }

    @Test
    void moneyTransfer_Valid_NoContent() throws Exception {
        Principal principal = () -> "Oli";
        TransferDTO TransferDTO = new TransferDTO(new Money(BigDecimal.valueOf(50)), 5L, 2L);
        String body = objectMapper.writeValueAsString(TransferDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/api/accounts/transfers/Oli/1").principal(principal)
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andReturn();
        assertEquals(BigDecimal.valueOf(350).setScale(2), accountRepository.findById(2L).get().getBalance().getAmount());
        assertEquals(BigDecimal.valueOf(250).setScale(2), accountRepository.findById(1L).get().getBalance().getAmount());
    }

    @Test
    void moneyTransfer_invalidUserLogged_Forbidden() throws Exception {
        Principal principal = () -> "Oli";
        TransferDTO TransferDTO = new TransferDTO(new Money(BigDecimal.valueOf(50)), 5L, 2L);
        String body = objectMapper.writeValueAsString(TransferDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/api/accounts/transfers/Iki/1").principal(principal)
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
    }
    @Test
    void moneyTransfer_invalidReceiverAccountId_NotFound() throws Exception {
        Principal principal = () -> "Oli";
        TransferDTO TransferDTO = new TransferDTO(new Money(BigDecimal.valueOf(50)), 5L, 3L);
        String body = objectMapper.writeValueAsString(TransferDTO);
        mockMvc.perform(patch("/api/accounts/transfers/Oli/1").principal(principal)
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
    }
    @Test
    void moneyTransfer_invalidAccountId_NotFound() throws Exception {
        Principal principal = () -> "Oli";
        TransferDTO TransferDTO = new TransferDTO(new Money(BigDecimal.valueOf(50)), 5L, 2L);
        String body = objectMapper.writeValueAsString(TransferDTO);
        mockMvc.perform(patch("/api/accounts/transfers/Oli/5").principal(principal)
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
    }
    @Test
    void moneyTransfer_invalidAccountOwner_NotFound() throws Exception {
        Principal principal = () -> "Oli";
        TransferDTO TransferDTO = new TransferDTO(new Money(BigDecimal.valueOf(50)), 4L, 2L);
        String body = objectMapper.writeValueAsString(TransferDTO);
        mockMvc.perform(patch("/api/accounts/transfers/Oli/1").principal(principal)
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
    }
    @Test
    void moneyTransfer_notEnoughFunds_BadRequest() throws Exception {
        Principal principal = () -> "Oli";
        TransferDTO TransferDTO = new TransferDTO(new Money(BigDecimal.valueOf(400)), 5L, 2L);
        String body = objectMapper.writeValueAsString(TransferDTO);
        mockMvc.perform(patch("/api/accounts/transfers/Oli/1").principal(principal)
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
    }
    @Test
    void moneyTransfer_noAccount_BadRequest() throws Exception {
        Principal principal = () -> "Oli";
        TransferDTO TransferDTO = new TransferDTO(new Money(BigDecimal.valueOf(400)), 5L, 5L);
        String body = objectMapper.writeValueAsString(TransferDTO);
        mockMvc.perform(patch("/api/accounts/transfers/Oli/1").principal(principal)
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
    }
    @Test
    void moneyTransfer_noOwner_BadRequest() throws Exception {
        Principal principal = () -> "Oli";
        TransferDTO TransferDTO = new TransferDTO(new Money(BigDecimal.valueOf(400)), 7L, 2L);
        String body = objectMapper.writeValueAsString(TransferDTO);
        mockMvc.perform(patch("/api/accounts/transfers/Oli/1").principal(principal)
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
    }
    @Test
    void moneyTransfer_Valid_penaltyFeeApplication() throws Exception {
        Principal principal = () -> "Oli";
        TransferDTO TransferDTO = new TransferDTO(new Money(BigDecimal.valueOf(100)), 5L, 2L);
        String body = objectMapper.writeValueAsString(TransferDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/api/accounts/transfers/Oli/1").principal(principal)
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andReturn();
        assertEquals(BigDecimal.valueOf(400).setScale(2), accountRepository.findById(2L).get().getBalance().getAmount());
        assertEquals(BigDecimal.valueOf(160).setScale(2), accountRepository.findById(1L).get().getBalance().getAmount());
    }

    @Test
    void sendMoney_invalidAccount_NotFound() throws Exception {
        ThirdPartyTransferDTO thirdPartyTransferDTO = new ThirdPartyTransferDTO(new Money(BigDecimal.valueOf(50)), 5L, "Pipipi", "Lalala");
        String body = objectMapper.writeValueAsString(thirdPartyTransferDTO);
        mockMvc.perform(patch("/api/accounts/sendMoney")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();

    }

    @Test
    void sendMoney_creditCardAccount_BadRequest() throws Exception {
        ThirdPartyTransferDTO thirdPartyTransferDTO = new ThirdPartyTransferDTO(new Money(BigDecimal.valueOf(50)), 3L, "Pipipi", "Lalala");
        String body = objectMapper.writeValueAsString(thirdPartyTransferDTO);
        mockMvc.perform(patch("/api/accounts/sendMoney")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    void sendMoney_invalidSecretKey_NotFound() throws Exception {
        ThirdPartyTransferDTO thirdPartyTransferDTO = new ThirdPartyTransferDTO(new Money(BigDecimal.valueOf(50)), 1L, "Papapa", "Lalala");
        String body = objectMapper.writeValueAsString(thirdPartyTransferDTO);
        mockMvc.perform(patch("/api/accounts/sendMoney")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();

    }
    @Test
    void sendMoney_invalidHashedKey_NotFound() throws Exception {
        ThirdPartyTransferDTO thirdPartyTransferDTO = new ThirdPartyTransferDTO(new Money(BigDecimal.valueOf(50)), 1L, "Pipipi", "Lala");
        String body = objectMapper.writeValueAsString(thirdPartyTransferDTO);
        mockMvc.perform(patch("/api/accounts/sendMoney")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();

    }
    @Test
    void receiveMoney_invalidAccount_NotFound() throws Exception {
        ThirdPartyTransferDTO thirdPartyTransferDTO = new ThirdPartyTransferDTO(new Money(BigDecimal.valueOf(50)), 5L, "Pipipi", "Lalala");
        String body = objectMapper.writeValueAsString(thirdPartyTransferDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/api/accounts/receiveMoney")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    void receiveMoney_CreditCard_BadRequest() throws Exception {
        ThirdPartyTransferDTO thirdPartyTransferDTO = new ThirdPartyTransferDTO(new Money(BigDecimal.valueOf(50)), 3L, "Pipipi", "Lalala");
        String body = objectMapper.writeValueAsString(thirdPartyTransferDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/api/accounts/receiveMoney")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
    }
    @Test
    void receiveMoney_invalidSecretKey_BadRequest() throws Exception {
        ThirdPartyTransferDTO thirdPartyTransferDTO = new ThirdPartyTransferDTO(new Money(BigDecimal.valueOf(50)), 1L, "Papapa", "Lalala");
        String body = objectMapper.writeValueAsString(thirdPartyTransferDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/api/accounts/receiveMoney")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
    }
    @Test
    void receiveMoney_invalidHashedKey_NotFound() throws Exception {
        ThirdPartyTransferDTO thirdPartyTransferDTO = new ThirdPartyTransferDTO(new Money(BigDecimal.valueOf(50)), 1L, "Pipipi", "Lala");
        String body = objectMapper.writeValueAsString(thirdPartyTransferDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/api/accounts/receiveMoney")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
    }
    @Test
    void receiveMoney_notEnoughFunds_BadRequest() throws Exception {
        ThirdPartyTransferDTO thirdPartyTransferDTO = new ThirdPartyTransferDTO(new Money(BigDecimal.valueOf(400)), 1L, "Pipipi", "Lalala");
        String body = objectMapper.writeValueAsString(thirdPartyTransferDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/api/accounts/receiveMoney")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
    }
    @Test
    void receiveMoney_feeApplication_NoContent() throws Exception {
        ThirdPartyTransferDTO thirdPartyTransferDTO = new ThirdPartyTransferDTO(new Money(BigDecimal.valueOf(100)), 1L, "Pipipi", "Lalala");
        String body = objectMapper.writeValueAsString(thirdPartyTransferDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/api/accounts/receiveMoney")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andReturn();
        assertEquals(BigDecimal.valueOf(160).setScale(2), accountRepository.findById(1L).get().getBalance().getAmount());
    }

}