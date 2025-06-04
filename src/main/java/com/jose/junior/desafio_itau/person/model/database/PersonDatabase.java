package com.jose.junior.desafio_itau.person.model.database;

import com.jose.junior.desafio_itau.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "person")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder(toBuilder = true)
@Value
public class PersonDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String fullname;
    LocalDate birthDate;

    String document;

    String email;

    String telephone;

    Boolean active;

    Boolean manageAccounts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    Account account;
}
