package com.jose.junior.desafio_itau.person.controller;

import com.jose.junior.desafio_itau.person.useCase.CreatePersonUseCase;
import com.jose.junior.desafio_itau.person.useCase.CreatePersonUseCase.CreatePersonCommand;
import com.jose.junior.desafio_itau.person.useCase.DisablePersonUseCase;
import com.jose.junior.desafio_itau.person.useCase.DisablePersonUseCase.DisablePersonCommand;
import com.jose.junior.desafio_itau.person.useCase.EnablePersonUseCase;
import com.jose.junior.desafio_itau.person.useCase.EnablePersonUseCase.EnablePersonCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.jose.junior.desafio_itau.person.controller.PersonController.PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    public static final String PATH = "/person/{managerDocument}";

    private final CreatePersonUseCase createPerson;
    private final EnablePersonUseCase enablePerson;
    private final DisablePersonUseCase disablePersonUseCase;

    @PostMapping
    public ResponseEntity<Void> postPerson(@RequestBody @Valid CreatePersonCommand cmd,
                                           @PathVariable String managerDocument) {
        createPerson.execute(cmd.withManageDocument(managerDocument));
        return ResponseEntity.created((URI.create("/person" + cmd.getDocument()))).build();
    }

    @PutMapping(path = "/enable")
    public ResponseEntity<Void> enablePerson(@RequestBody @Valid EnablePersonCommand cmd,
                                             @PathVariable String managerDocument) {
        enablePerson.execute(cmd.withManagerDocument(managerDocument));
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/disable")
    public ResponseEntity<Void> disablePerson(@RequestBody @Valid DisablePersonCommand cmd,
                                              @PathVariable String managerDocument) {
        disablePersonUseCase.execute(cmd.withManagerDocument(managerDocument));
        return ResponseEntity.ok().build();
    }
}
