package com.jose.junior.desafio_itau.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jose.junior.desafio_itau.account.gateway.database.AccountRepository;
import com.jose.junior.desafio_itau.account.model.database.AccountDatabase;
import com.jose.junior.desafio_itau.account.useCase.AddBalanceUseCase.AddBalanceCommand;
import com.jose.junior.desafio_itau.account.useCase.CreateAccountUseCase.CreateAccountCommand;
import com.jose.junior.desafio_itau.account.useCase.DebitBalanceUseCase.DebitBalanceCommand;
import com.jose.junior.desafio_itau.account.useCase.DisableAccountUseCase.DisableAccountCommand;
import com.jose.junior.desafio_itau.account.useCase.GetAccountUseCase.AccountDTO;
import com.jose.junior.desafio_itau.person.gateway.database.PersonRepository;
import com.jose.junior.desafio_itau.person.model.database.PersonDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.jose.junior.desafio_itau.account.controller.AccountController.PATH;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(PER_CLASS)
@ActiveProfiles("test")
public class AccountControllerIntegrationTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    AccountRepository repository;

    @Autowired
    private MockMvc mvc;

    private static final String ACCOUNT_ENDPOINT = PATH;
    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void beforeAll() {
        personRepository.deleteAll();
        repository.deleteAll();
    }

    @Test
    @DisplayName("Ensures that an account will be opened successfully")
    public void shoudlOpenAccountWithSuccess() throws Exception {

        var manager = PersonDatabase.builder()
                .birthDate(LocalDate.of(1990, 2, 3))
                .document("66721724243")
                .email("teste@teste.com")
                .fullName("Alisson Santos")
                .manageAccounts(true)
                .active(true)
                .telephone("123345567")
                .build();
        personRepository.save(manager);

        var personDatabase = PersonDatabase.builder()
                .birthDate(LocalDate.of(1985, 9, 1))
                .document("26204434071")
                .email("joao@teste.com")
                .fullName("João da silva")
                .manageAccounts(false)
                .active(false)
                .telephone("123345567")
                .build();
        personRepository.save(personDatabase);

        var cmd = CreateAccountCommand.builder()
                .personDocument(personDatabase.getDocument())
                .build();

        var result = mvc.perform(post(ACCOUNT_ENDPOINT.concat("/{managerDocument}"), manager.getDocument())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cmd)))
                .andExpect(status().isCreated())
                .andReturn();

        String location = result.getResponse().getHeader("Location");
        String accountId = location.substring(location.lastIndexOf('/') + 1);

        var accountDb = accountRepository.findById(Long.parseLong(accountId)).get().toDomain(true);

        assertAll("Ensures that the account was opened successfully with the customer data provided",
                () -> assertNotNull(accountDb),
                () -> assertTrue(accountDb.getActive()),
                () -> assertEquals(0, accountDb.getBalance().compareTo(new BigDecimal("0.00"))),
                () -> assertEquals(cmd.getPersonDocument(), accountDb.getClient().getDocument())
        );
    }

    @Test
    @DisplayName("Ensures that the account was successfully deactivated without changing the data")
    public void shouldDeactivateAccountWithSuccess() throws Exception {

        var manager = PersonDatabase.builder()
                .birthDate(LocalDate.of(1990, 2, 3))
                .document("66721724243")
                .email("teste@teste.com")
                .fullName("Alisson Santos")
                .manageAccounts(true)
                .active(true)
                .telephone("123345567")
                .build();
        personRepository.save(manager);

        var personDatabase = PersonDatabase.builder()
                .birthDate(LocalDate.of(1985, 9, 1))
                .document("26204434071")
                .email("joao@teste.com")
                .fullName("João da silva")
                .manageAccounts(false)
                .active(false)
                .telephone("123345567")
                .build();
        personRepository.save(personDatabase);

        var account = AccountDatabase.builder()
                .balance(BigDecimal.ZERO)
                .active(true)
                .client(personDatabase)
                .build();
        var accountDb = accountRepository.save(account);

        var cmd = DisableAccountCommand.builder()
                .accountId(account.getId())
                .build();

        mvc.perform(put(ACCOUNT_ENDPOINT.concat("/disable/{managerDocument}"), manager.getDocument())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cmd)))
                .andExpect(status().isOk());

        var result = accountRepository.findById(accountDb.getId()).get().toDomain(true);

        assertAll("Validating account data",
                () -> assertFalse(result.getActive()),
                () -> assertEquals(0, result.getBalance().compareTo(new BigDecimal("0.00"))),
                () -> assertEquals(result.getClient().getDocument(), personDatabase.getDocument())
        );
    }

    @Test
    @DisplayName("Ensures that the account has been successfully activated without changing data")
    public void shouldActivateAccountWithSuccess() throws Exception {

        var manager = PersonDatabase.builder()
                .birthDate(LocalDate.of(1990, 2, 3))
                .document("66721724243")
                .email("teste@teste.com")
                .fullName("Alisson Santos")
                .manageAccounts(true)
                .active(true)
                .telephone("123345567")
                .build();
        personRepository.save(manager);

        var personDatabase = PersonDatabase.builder()
                .birthDate(LocalDate.of(1985, 9, 1))
                .document("26204434071")
                .email("joao@teste.com")
                .fullName("João da silva")
                .manageAccounts(false)
                .active(false)
                .telephone("123345567")
                .build();
        personRepository.save(personDatabase);

        var account = AccountDatabase.builder()
                .balance(BigDecimal.ZERO)
                .active(false)
                .client(personDatabase)
                .build();
        accountRepository.save(account);

        var cmd = DisableAccountCommand.builder()
                .accountId(account.getId())
                .build();

        mvc.perform(put(ACCOUNT_ENDPOINT.concat("/enable/{managerDocument}"), manager.getDocument())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cmd)))
                .andExpect(status().isOk());

        var result = accountRepository.findById(1L).get().toDomain(true);

        assertAll("Validating account data",
                () -> assertTrue(result.getActive()),
                () -> assertEquals(0, result.getBalance().compareTo(new BigDecimal("0.00"))),
                () -> assertEquals(result.getClient().getDocument(), personDatabase.getDocument())
        );
    }

    @Test
    @DisplayName("Ensures the balance was added to the account without changing the other information")
    public void shouldAddBalanceToAccountWithSuccess() throws Exception {

        var personDatabase = PersonDatabase.builder()
                .birthDate(LocalDate.of(1985, 9, 1))
                .document("26204434071")
                .email("joao@teste.com")
                .fullName("João da silva")
                .manageAccounts(false)
                .active(false)
                .telephone("123345567")
                .build();
        personRepository.save(personDatabase);

        var account = AccountDatabase.builder()
                .balance(BigDecimal.ZERO)
                .active(true)
                .client(personDatabase)
                .build();
        var accountDb = accountRepository.save(account);

        var cmd = AddBalanceCommand
                .builder()
                .accountId(account.getId())
                .documentAccountOwner(personDatabase.getDocument())
                .time(LocalDateTime.now())
                .valueForAdd(BigDecimal.valueOf(2500.75))
                .build();

        mvc.perform(put(ACCOUNT_ENDPOINT.concat("/add-balance/{accountOwner}"), personDatabase.getDocument())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cmd)))
                .andExpect(status().isOk());

        var resultDb = accountRepository.findById(accountDb.getId()).get().toDomain(true);

        assertAll("Validating account data",
                () -> assertTrue(resultDb.getActive()),
                () -> assertEquals(0, resultDb.getBalance().compareTo(new BigDecimal("2500.75"))),
                () -> assertEquals(resultDb.getClient().getDocument(), personDatabase.getDocument())
        );
    }

    @Test
    @DisplayName("Ensures the balance was debited to the account without changing the other information")
    public void shouldDebitBalanceToAccountWithSuccess() throws Exception {

        var personDatabase = PersonDatabase.builder()
                .birthDate(LocalDate.of(1985, 9, 1))
                .document("26204434071")
                .email("joao@teste.com")
                .fullName("João da silva")
                .manageAccounts(false)
                .active(false)
                .telephone("123345567")
                .build();
        personRepository.save(personDatabase);

        var account = AccountDatabase.builder()
                .balance(BigDecimal.valueOf(2503.97))
                .active(true)
                .client(personDatabase)
                .build();
        var accountDb = accountRepository.save(account);

        var cmd = DebitBalanceCommand.builder()
                .accountId(account.getId())
                .documentAccountOwner(personDatabase.getDocument())
                .time(LocalDateTime.now())
                .valueForDebit(BigDecimal.valueOf(1500.00))
                .build();

        mvc.perform(put(ACCOUNT_ENDPOINT.concat("/debit-balance/{accountOwner}"), personDatabase.getDocument())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cmd)))
                .andExpect(status().isOk());

        var resultDb = accountRepository.findById(accountDb.getId()).get().toDomain(true);

        assertAll("Validating account data",
                () -> assertTrue(resultDb.getActive()),
                () -> assertEquals(0, resultDb.getBalance().compareTo(new BigDecimal("1003.97"))),
                () -> assertEquals(resultDb.getClient().getDocument(), personDatabase.getDocument())
        );
    }

    @Test
    @DisplayName("Ensures that the account data returned is correct.")
    public void shouldRRetunrAccountDateWithSuccess() throws Exception {

        var personDatabase = PersonDatabase.builder()
                .birthDate(LocalDate.of(1985, 9, 1))
                .document("26204434071")
                .email("joao@teste.com")
                .fullName("João da silva")
                .manageAccounts(false)
                .active(false)
                .telephone("123345567")
                .build();
        personRepository.save(personDatabase);

        var account = AccountDatabase.builder()
                .balance(BigDecimal.valueOf(2503.97))
                .active(true)
                .client(personDatabase)
                .build();
        var accountId = accountRepository.save(account).getId();

        var resultData = mvc.perform(get(ACCOUNT_ENDPOINT.concat("/{accountOwner}"), personDatabase.getDocument()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var accountDtoResponse = mapper.readValue(resultData, AccountDTO.class);

        assertAll("teste",
                () -> assertEquals(accountDtoResponse.getDocumentOwner(), personDatabase.getDocument()),
                () -> assertEquals(accountDtoResponse.getId(), accountId),
                () -> assertEquals(accountDtoResponse.getBalance(), BigDecimal.valueOf(2503.97)),
                () -> assertTrue(accountDtoResponse.getActive())
        );
    }
}