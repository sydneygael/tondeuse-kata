package org.domain.command.impl;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.domain.enums.OrientationEnum;
import org.domain.models.entities.Tondeuse;
import org.domain.command.TondeuseCommande;
import org.domain.models.valueobjects.Position;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class PivoterDroiteCommande implements TondeuseCommande {

    private Tondeuse tondeuse;

    @Override
    public String execute() {
        var currentPosition = tondeuse.getPosition();
        var newPosition = Position.at(currentPosition.positionX(),currentPosition.positionY())
                        .pointing(pivotRight(currentPosition.orientation()));
        tondeuse.setPosition(newPosition);
        var newPositionStr = newPosition.afficher();
        log.info("Pivoter à gauche: Nouvelle orientation - {}", newPositionStr);
        return newPositionStr;
    }

    private OrientationEnum pivotRight(OrientationEnum orientation) {
        return switch (orientation) {
            case NORTH -> OrientationEnum.EAST;
            case WEST -> OrientationEnum.NORTH;
            case SOUTH -> OrientationEnum.WEST;
            case EAST -> OrientationEnum.SOUTH;
        };
    }
}
