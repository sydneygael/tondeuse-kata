package org.domain.service;

import lombok.AllArgsConstructor;
import org.domain.command.impl.AvancerCommande;
import org.domain.command.impl.PivoterDroiteCommande;
import org.domain.command.impl.PivoterGaucheCommande;
import org.domain.enums.CommandEnum;
import org.domain.exceptions.UnknownCommandException;
import org.domain.ports.input.MoveTondeusePort;

@AllArgsConstructor
public class TondeuseService implements MoveTondeusePort {

    @Override
    public String handle(TondeuseMoveRequest request) throws UnknownCommandException {
        var result = "";
        for (CommandEnum commandEnum : request.commands()) {
            switch (commandEnum) {
                case ADVANCE -> result = new AvancerCommande(request.tondeuse(), request.surface()).execute();
                case LEFT -> result = new PivoterGaucheCommande(request.tondeuse()).execute();
                case RIGHT -> result = new PivoterDroiteCommande(request.tondeuse()).execute();
                default -> {
                    throw new UnknownCommandException("Unknown command");
                }
            }
        }

        return result;
    }
}
