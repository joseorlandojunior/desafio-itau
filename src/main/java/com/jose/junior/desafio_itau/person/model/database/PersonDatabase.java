package com.jose.junior.desafio_itau.person.model.database;

import com.jose.junior.desafio_itau.account.model.database.AccountDatabase;
import com.jose.junior.desafio_itau.person.model.domain.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@Entity(name = "person")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder(toBuilder = true)
@Value
public class PersonDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long id;

    @Column
    String fullName;

    @Column
    LocalDate birthDate;

    @Column
    String document;

    @Column
    String email;

    @Column
    String telephone;

    @Column
    Boolean active;

    @Column
    Boolean manageAccounts;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    AccountDatabase account;

    public Person toDomain(boolean includeAccount) {
        return Person.builder()
                .id(id)
                .document(document)
                .email(email)
                .telephone(telephone)
                .active(active)
                .manageAccounts(manageAccounts)
                .fullname(fullName)
                .birthDate(birthDate)
                .account(includeAccount && account != null ? account.toDomain(false) : null)
                .build();
    }
}