package org.application.adapters.rest;

import org.application.ports.input.MoveTondeusePort;
import org.application.usescases.DeplacerTondeuse;
import org.domain.exceptions.UnknownCommandException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tondeuse")
public class TondeuseController {

    private final DeplacerTondeuse deplacerTondeuseUseCase;

    public TondeuseController(DeplacerTondeuse deplacerTondeuse) {
        this.deplacerTondeuseUseCase = deplacerTondeuse;
    }

    @PostMapping("/command")
    public ResponseEntity<String> envoyerCommande(@RequestBody MoveTondeusePort.DeplacerTondeuseRequete request) {
        try {
            return ResponseEntity.ok(deplacerTondeuseUseCase.handle(request));
        } catch (UnknownCommandException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
