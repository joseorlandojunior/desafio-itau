package com.jose.junior.desafio_itau.person.model.database;

import com.jose.junior.desafio_itau.account.model.database.AccountDatabase;
import com.jose.junior.desafio_itau.person.model.domain.Person;
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

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    AccountDatabase account;

    public Person toDomain() {
        return Person.builder()
                .id(id)
                .document(document)
                .email(email)
                .telephone(telephone)
                .active(active)
                .manageAccounts(manageAccounts)
                .fullname(fullname)
                .birthDate(birthDate)
                .account(account != null ? account.toDomain() : null)
                .build();
    }
}