package org.domain.models.valueobjects;

/**
 * Représente un value object
 */
public record Position(int positionX, int positionY) {
    public static Position of(int x, int y) {
        return new Position(x,y);
    }
}
