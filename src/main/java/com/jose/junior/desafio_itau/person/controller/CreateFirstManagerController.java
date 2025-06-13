package com.jose.junior.desafio_itau.person.controller;

import com.jose.junior.desafio_itau.person.useCase.CreateFirstManagerUseCase;
import com.jose.junior.desafio_itau.person.useCase.CreateFirstManagerUseCase.CreateFirstManagerCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.jose.junior.desafio_itau.person.controller.CreateFirstManagerController.PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class CreateFirstManagerController {

    public static final String PATH = "/first-manager";

    private final CreateFirstManagerUseCase createFirstManager;

    @PostMapping
    public ResponseEntity<Void> createFirManager(@RequestBody @Valid CreateFirstManagerCommand cmd) {
        createFirstManager.execute(cmd);
        return ResponseEntity.created(URI.create("/" + cmd.getDocument())).build();
    }
}
