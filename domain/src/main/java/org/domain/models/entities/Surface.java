package org.domain.models.entities;

import org.domain.models.valueobjects.Position;

public interface Surface {
    /**
     * Vérifie si une position est dans le type de surface
     * @param position
     * @return
     */
    boolean isInside(Position position);
}
