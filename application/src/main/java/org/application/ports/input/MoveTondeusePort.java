package org.application.ports.input;

import org.domain.enums.CommandEnum;
import org.domain.exceptions.UnknownCommandException;
import org.domain.models.entities.SurfaceRectangle;
import org.domain.models.entities.Tondeuse;

import java.util.List;

public interface MoveTondeusePort {
    String handle(DeplacerTondeuseRequete request) throws UnknownCommandException;

    record DeplacerTondeuseRequete(List<CommandEnum> commands, Tondeuse tondeuse, SurfaceRectangle surface) { }
}
