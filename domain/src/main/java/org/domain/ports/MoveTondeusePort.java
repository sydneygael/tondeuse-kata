package org.domain.ports;

import org.domain.enums.CommandEnum;
import org.domain.exceptions.UnknownCommandException;
import org.domain.models.entities.Surface;
import org.domain.models.entities.Tondeuse;

public interface MoveTondeusePort {
    boolean handle(TondeuseMoveRequest request) throws UnknownCommandException;

    record TondeuseMoveRequest(CommandEnum command, Tondeuse tondeuse, Surface surface) { }
}
