package org.domain.factory;

import org.domain.enums.CommandEnum;
import org.domain.models.entities.Surface;
import org.domain.models.entities.Tondeuse;
import org.domain.ports.TondeuseCommande;

public interface TondeuseCommandeFactory {
    TondeuseCommande getCommand(CommandEnum type, Tondeuse tondeuse, Surface surface);
}
