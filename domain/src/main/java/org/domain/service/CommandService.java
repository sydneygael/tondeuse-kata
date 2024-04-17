package org.domain.service;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.domain.enums.CommandEnum;
import org.domain.exceptions.UnknownCommandException;
import org.domain.factory.TondeuseCommandeFactory;
import org.domain.models.entities.Surface;
import org.domain.models.entities.Tondeuse;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Slf4j
public class CommandService {
    private Tondeuse tondeuse;
    private Surface surface;
    private final TondeuseCommandeFactory factory;

    public CommandService(TondeuseCommandeFactory tondeuseCommandeFactory) {
        this.factory = tondeuseCommandeFactory;
    }

    public boolean executeCommand(CommandEnum command, Tondeuse tondeuse, Surface surface) throws UnknownCommandException {
        switch (command) {
            case ADVANCE, RIGHT, LEFT -> {
                return factory.getCommand(command, tondeuse, surface).execute();
            }
            default -> {
                log.error("Unknown command: {}", command);
                throw new UnknownCommandException("Unknown command");
            }
        }
    }
}
