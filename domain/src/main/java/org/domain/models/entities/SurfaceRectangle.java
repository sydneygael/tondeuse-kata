package org.domain.models.entities;

import lombok.*;
import org.domain.models.valueobjects.Position;

/**
 * Entité représentant la grille sur laquelle va passer la pelouse
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class SurfaceRectangle implements Surface {

    Position positionInitial;
    private int hauteur;
    private int largeur;



    @Override
    public boolean contientPosition(Position position) {
        return position.positionX() >= positionInitial.positionX() &&
                position.positionX() <= positionInitial.positionX() + hauteur &&
                position.positionY() >= positionInitial.positionY() &&
                position.positionY() <= positionInitial.positionY() + largeur;
    }
}
