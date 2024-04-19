package org.domain.command.impl;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.domain.enums.OrientationEnum;
import org.domain.models.entities.Tondeuse;
import org.domain.command.TondeuseCommande;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class PivoterGaucheCommande implements TondeuseCommande {

    private Tondeuse tondeuse;

    @Override
    public String execute() {
        OrientationEnum newOrientation = pivotLeft(tondeuse.getOrientation());
        tondeuse.setOrientation(newOrientation);
        log.info("Pivoter à gauche: Nouvelle orientation - {}", newOrientation);
        return tondeuse.afficher();
    }

    private OrientationEnum pivotLeft(OrientationEnum orientation) {
        return switch (orientation) {
            case NORTH -> OrientationEnum.WEST;
            case WEST -> OrientationEnum.SOUTH;
            case SOUTH -> OrientationEnum.EAST;
            case EAST -> OrientationEnum.NORTH;
        };
    }
}
