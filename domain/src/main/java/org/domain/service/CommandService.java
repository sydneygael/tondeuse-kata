package org.domain.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.domain.exceptions.UnknownCommandException;
import org.domain.factory.TondeuseCommandeFactory;
import org.domain.factory.TondeuseCommandeFactoryImpl;
import org.domain.ports.MoveTondeusePort;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Slf4j
public class CommandService implements MoveTondeusePort {

    private final TondeuseCommandeFactory factory;

    public CommandService() {
        factory = new TondeuseCommandeFactoryImpl();
    }

    public CommandService(TondeuseCommandeFactory tondeuseCommandeFactory) {
        this.factory = tondeuseCommandeFactory;
    }


    @Override
    public boolean handle(TondeuseMoveRequest request) throws UnknownCommandException {
        var command = request.command();
        switch (command) {
            case ADVANCE, RIGHT, LEFT -> {
                return factory.getCommand(command, request.tondeuse(), request.surface()).execute();
            }
            default -> {
                log.error("Unknown command: {}", command);
                throw new UnknownCommandException("Unknown command");
            }
        }
    }
}
