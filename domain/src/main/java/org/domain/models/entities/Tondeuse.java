package org.domain.models.entities;

import lombok.*;
import org.domain.enums.OrientationEnum;
import org.domain.models.valueobjects.Position;

/**
 * Classe représentant l'entité tondeuse
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Tondeuse {

    private Integer id;
    private Position position;
    private OrientationEnum orientation;

    public String afficher() {
        return position.positionX() + " " + position.positionY() + " " + orientation.getCode();
    }

}
