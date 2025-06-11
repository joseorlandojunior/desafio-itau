package com.jose.junior.desafio_itau.account.controller;

import com.jose.junior.desafio_itau.account.useCase.GetAccountUseCase;
import com.jose.junior.desafio_itau.account.useCase.*;
import com.jose.junior.desafio_itau.account.useCase.AddBalanceUseCase.AddBalanceCommand;
import com.jose.junior.desafio_itau.account.useCase.CreateAccountUseCase.CreateAccountCommand;
import com.jose.junior.desafio_itau.account.useCase.DebitBalanceUseCase.DebitBalanceCommand;
import com.jose.junior.desafio_itau.account.useCase.EnableAccountUseCase.EnableAccountCommand;
import com.jose.junior.desafio_itau.account.useCase.GetAccountUseCase.AccountDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.jose.junior.desafio_itau.account.controller.AccountController.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = PATH, produces = APPLICATION_JSON_VALUE)
public class AccountController {

    public static final String PATH = "/accounts";

    private final CreateAccountUseCase createAccount;
    private final EnableAccountUseCase enableAccount;
    private final DisableAccountUseCase disableAccount;
    private final AddBalanceUseCase addBalance;
    private final DebitBalanceUseCase debitBalance;
    private final GetAccountUseCase getAccountUseCase;

    @PostMapping("/{managerDocument}")
    public ResponseEntity<Void> openAccount(@PathVariable String managerDocument,
                                            @RequestBody @Valid CreateAccountCommand createAccountCommand) {
        var accountId = createAccount.execute(createAccountCommand.withManagerDocument(managerDocument));
        return ResponseEntity.created(URI.create(String.format("/account/%s", accountId.toString()))).build();
    }

    @GetMapping("/{accountOwner}")
    public ResponseEntity<AccountDTO> getAaccountData(@PathVariable String accountOwner){
        var account = getAccountUseCase.execute(accountOwner);
        return ResponseEntity.ok(account);
    }

    @PutMapping("/enable/{managerDocument}")
    public ResponseEntity<Void> enableAccount(@PathVariable String managerDocument,
                                              @RequestBody @Valid EnableAccountCommand enableAccountCommand) {
        enableAccount.execute(enableAccountCommand.withManagerDocument(managerDocument));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/disable/{managerDocument}")
    public ResponseEntity<Void> disableAccount(@PathVariable String managerDocument,
                                               @RequestBody @Valid DisableAccountUseCase.DisableAccountCommand disableAccountCommand) {
        disableAccount.execute(disableAccountCommand.withManagerDocument(managerDocument));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/add-balance/{accountOwner}")
    public ResponseEntity<Void> creditBalance(@PathVariable String accountOwner,
                                              @RequestBody @Valid AddBalanceCommand addBalanceCommand) {
        addBalance.execute(addBalanceCommand.withOwner(accountOwner));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/debit-balance/{accountOwner}")
    public ResponseEntity<Void> debitBalance(@PathVariable String accountOwner,
                                             @RequestBody @Valid DebitBalanceCommand debitBalanceCommand) {
        debitBalance.execute(debitBalanceCommand.withOwner(accountOwner));
        return ResponseEntity.ok().build();
    }
}