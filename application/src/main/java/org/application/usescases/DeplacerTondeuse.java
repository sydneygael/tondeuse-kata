package org.application.usescases;

import lombok.AllArgsConstructor;
import org.application.ports.input.MoveTondeusePort;
import org.domain.exceptions.UnknownCommandException;
import org.domain.service.TondeuseService;

@UseCase
@AllArgsConstructor
public class DeplacerTondeuse implements MoveTondeusePort {
    private final TondeuseService tondeuseService;
    @Override
    public String handle(DeplacerTondeuseRequete request) throws UnknownCommandException {
        return tondeuseService.deplacerTondeuse(request.commands(),request.tondeuse(),request.surface());
    }
}
