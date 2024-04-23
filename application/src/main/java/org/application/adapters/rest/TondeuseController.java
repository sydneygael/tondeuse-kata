package org.application.adapters.rest;

import org.domain.exceptions.UnknownCommandException;
import org.domain.ports.input.MoveTondeusePort;
import org.domain.service.TondeuseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tondeuse")
public class TondeuseController {

    private final MoveTondeusePort moveTondeusePort;

    public TondeuseController(TondeuseService moveTondeusePort) {
        this.moveTondeusePort = moveTondeusePort;
    }

    @PostMapping("/command")
    public ResponseEntity<String> sendCommand(@RequestBody MoveTondeusePort.TondeuseMoveRequest request) {
        try {
            var result = moveTondeusePort.handle(request);
            return ResponseEntity.ok(result);
        } catch (UnknownCommandException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
