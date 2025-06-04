package com.jose.junior.desafio_itau.person.controller;

import com.jose.junior.desafio_itau.person.CreatePersonUseCase;
import com.jose.junior.desafio_itau.person.CreatePersonUseCase.CreatePersonCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.jose.junior.desafio_itau.person.controller.PersonController.*;

@SuppressWarnings("ALL")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    public static final String PATH = "/person/{gerenteDocument}";

    private final CreatePersonUseCase createPerson;

    @PostMapping()
    public String postPerson(@RequestBody CreatePersonCommand cmd,
                           @PathVariable String gerenteDocument) {
        createPerson.execute(cmd);
    }

    @PostMapping("/manager")
    public void postManager(@RequestBody CreatePersonCommand cmd) {
        createPerson.execute(cmd);
    }


    @PutMapping("/enable")
    public void enablePerson(@RequestBody CreatePersonCommand cmd) {
        createPerson.execute(cmd);
    }

    @PutMapping("/disable")
    public void disablePerson(@RequestBody CreatePersonCommand cmd) {
        createPerson.execute(cmd);
    }
}
