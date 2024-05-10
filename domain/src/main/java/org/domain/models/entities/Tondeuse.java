package org.domain.models.entities;

import lombok.*;
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

}
