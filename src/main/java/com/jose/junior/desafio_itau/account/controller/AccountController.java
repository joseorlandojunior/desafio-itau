package com.jose.junior.desafio_itau.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    @PostMapping
    public String openAccount() {
        return "Deu certo";
    }

    @PostMapping("/enable/{id}")
    public String enableAccount() {
        return "Deu certo";
    }

    @PostMapping("/disable/{id}")
    public String disableAccount() {
        return "Deu certo";
    }

    @PostMapping("/credit-balance/{id}")
    public String creditBalance() {
        return "Deu certo";
    }

    @PostMapping("/debit-balance/{id}")
    public String debitBalance() {
        return "Deu certo";
    }
}
