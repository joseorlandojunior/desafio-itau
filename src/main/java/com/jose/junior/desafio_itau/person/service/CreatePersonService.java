package com.jose.junior.desafio_itau.person.service;

import com.jose.junior.desafio_itau.person.CreatePersonUseCase;
import com.jose.junior.desafio_itau.person.model.domain.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePersonService implements CreatePersonUseCase {

    private final PersonService service;

    @Override
    public void execute(CreatePersonCommand cmd) {
        var managerIsActive = service.existsByDocumentAndActive(cmd.getManagerDocument(), true);

        if (managerIsActive) {
            service.save(buildPerson(cmd));
        }
    }

    private Person buildPerson(CreatePersonCommand cmd) {
        return Person.builder()
                .active(true)
                .email(cmd.getEmail())
                .birthDate(cmd.getBirthDate())
                .document(cmd.getDocument())
                .manageAccounts(false)
                .fullname(cmd.getFullName())
                .telephone(cmd.getTelephone())
                .build();
    }
}
