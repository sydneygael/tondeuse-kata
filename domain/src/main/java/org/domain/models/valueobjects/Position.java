package org.domain.models.valueobjects;

import org.domain.enums.OrientationEnum;

/**
 * Représente un value object
 */
public record Position(int positionX, int positionY, OrientationEnum orientation) {
    public static Position of(int x, int y, OrientationEnum orientation) {
        return new Position(x, y, orientation);
    }

    public static PositionBuilder at(int x, int y) {
        return new PositionBuilder(x, y);
    }

    public String afficher() {
        return this.positionX + " " + this.positionY + " " + this.orientation.getCode();
    }

    public static class PositionBuilder {
        private final int x;
        private final int y;

        public PositionBuilder(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position pointing(OrientationEnum orientation) {
            return new Position(x, y, orientation);
        }
    }
}
