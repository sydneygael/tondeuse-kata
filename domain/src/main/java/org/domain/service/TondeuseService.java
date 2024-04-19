package org.domain.service;

import lombok.AllArgsConstructor;
import org.domain.enums.CommandEnum;
import org.domain.exceptions.UnknownCommandException;
import org.domain.factory.TondeuseCommandeFactory;
import org.domain.ports.input.MoveTondeusePort;

@AllArgsConstructor
public class TondeuseService implements MoveTondeusePort {

    private final TondeuseCommandeFactory factory;

    @Override
    public String handle(TondeuseMoveRequest request) throws UnknownCommandException {
        var result = "";
        for (CommandEnum commandEnum : request.commands()) {
            switch (commandEnum) {
                case ADVANCE, RIGHT, LEFT -> {
                    result = factory.getCommand(commandEnum, request.tondeuse(), request.surface()).execute();
                    return result;
                }
                default -> {
                    throw new UnknownCommandException("Unknown command");
                }
            }
        }

        return result;
    }
}
