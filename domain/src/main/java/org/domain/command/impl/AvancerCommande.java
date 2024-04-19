package org.domain.command.impl;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.domain.enums.OrientationEnum;
import org.domain.models.entities.Surface;
import org.domain.models.entities.Tondeuse;
import org.domain.models.valueobjects.Position;
import org.domain.command.TondeuseCommande;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class AvancerCommande implements TondeuseCommande {

    private Tondeuse tondeuse;
    private Surface surface;

    @Override
    public String execute() {
        Position currentPosition = tondeuse.getPosition();
        Position newPosition = moveForward(currentPosition, tondeuse.getOrientation());

        if (surface.isInside(newPosition)) {
            tondeuse.setPosition(newPosition);
            var result = tondeuse.afficher();
            log.info("Avancer: Nouvelle position - {}", result);
            return result;
        } else {
            log.info("Avancer: Mouvement impossible, la tondeuse reste à sa position actuelle - {}", tondeuse.getPosition());
            return tondeuse.afficher();
        }
    }

    private Position moveForward(Position currentPosition, OrientationEnum orientation) {
        int x = currentPosition.positionX();
        int y = currentPosition.positionY();

        switch (orientation) {
            case NORTH -> Position.of(x, y + 1);
            case SOUTH -> Position.of(x, y - 1);
            case EST -> Position.of(x + 1, y);
            case WEST -> Position.of(x - 1, y);
        }
        return currentPosition;
    }
}
