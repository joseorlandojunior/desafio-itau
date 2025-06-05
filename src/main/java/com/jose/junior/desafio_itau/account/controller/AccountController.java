package com.jose.junior.desafio_itau.account.controller;

import com.jose.junior.desafio_itau.account.useCase.*;
import com.jose.junior.desafio_itau.account.useCase.AddBalanceUseCase.AddBalanceCommand;
import com.jose.junior.desafio_itau.account.useCase.CreateAccountUseCase.CreateAccountCommand;
import com.jose.junior.desafio_itau.account.useCase.EnableAccountUseCase.EnableAccountCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final CreateAccountUseCase createAccount;
    private final EnableAccountUseCase enableAccount;
    private final DisableAccountUseCase disableAccount;
    private final AddBalanceUseCase addBalance;
    private final DebitBalanceUseCase debitBalance;

    @PostMapping(path = "/{managerAccount}")
    public ResponseEntity<Void> openAccount(@PathVariable String managerAccount,
                                            @RequestBody CreateAccountCommand createAccountCommand) {
        createAccount.execute(createAccountCommand.withManagerDocument(managerAccount));
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/enable/{managerAccount}")
    public ResponseEntity<Void> enableAccount(@PathVariable String managerAccount,
                                              @RequestBody EnableAccountCommand enableAccountCommand) {
        enableAccount.execute(enableAccountCommand.withManagerDocument(managerAccount));
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/disable/{managerAccount}")
    public ResponseEntity<Void> disableAccount(@PathVariable String managerAccount,
                                               @RequestBody DisableAccountUseCase.DisableAccountCommand disableAccountCommand) {
        disableAccount.execute(disableAccountCommand.withManagerDocument(managerAccount));
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/add-balance/{accountOwner}")
    public ResponseEntity<Void> creditBalance(@PathVariable String accountOwner,
                                              @RequestBody AddBalanceCommand addBalanceCommand) {
        addBalance.execute(addBalanceCommand.withOwner(accountOwner));
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/debit-balance/{id}")
    public ResponseEntity<Void> debitBalance() {
        return ResponseEntity.ok().build();
    }
}
