package org.domain.ports.input;

import org.domain.enums.CommandEnum;
import org.domain.exceptions.UnknownCommandException;
import org.domain.models.entities.SurfaceRectangle;
import org.domain.models.entities.Tondeuse;

import java.util.List;

public interface MoveTondeusePort {
    String handle(TondeuseMoveRequest request) throws UnknownCommandException;

    record TondeuseMoveRequest(List<CommandEnum> commands, Tondeuse tondeuse, SurfaceRectangle surface) { }
}
