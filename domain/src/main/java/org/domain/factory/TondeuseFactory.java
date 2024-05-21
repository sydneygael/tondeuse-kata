package org.domain.factory;

import org.domain.enums.OrientationEnum;
import org.domain.exceptions.OrientationNotFoundException;
import org.domain.models.entities.Surface;
import org.domain.models.entities.SurfaceRectangle;
import org.domain.models.entities.Tondeuse;
import org.domain.models.valueobjects.Position;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TondeuseFactory {

    public static final Pattern COORDINATE_PATTERN = Pattern.compile("(\\d+) (\\d+) ([NEWS])");
    private static final Pattern DIMENTIONS_PATTERN = Pattern.compile("(\\d+) (\\d+)");
    public static final Pattern INSTRUCTIONS_PATTERN = Pattern.compile("[ADG]+");

    public Tondeuse creerTondeuse(String coordinateLine, int index) throws OrientationNotFoundException {
        Matcher matcher = COORDINATE_PATTERN.matcher(coordinateLine);
        if (matcher.matches()) {
            int positionX = Integer.parseInt(matcher.group(1));
            int positionY = Integer.parseInt(matcher.group(2));
            var orientation = OrientationEnum.of(String.valueOf(matcher.group(3)));
            var position = Position.of(positionX, positionY, orientation);
            return new Tondeuse(index, position);
        } else {
            throw new IllegalArgumentException("Format de ligne de position invalide: " + coordinateLine);
        }
    }

    public Surface creerSurfaceRectangle(String lignePositionRectangle) {
        var rectangleDimensions = extraireLesDimensionsDeLaSurface(lignePositionRectangle);
        return new SurfaceRectangle(Position.of(0, 0, null), rectangleDimensions[0], rectangleDimensions[1]);
    }

    private int[] extraireLesDimensionsDeLaSurface(String line) {
        Matcher matcher = DIMENTIONS_PATTERN.matcher(line);
        if (matcher.matches()) {
            int positionX = Integer.parseInt(matcher.group(1));
            int positionY = Integer.parseInt(matcher.group(2));
            return new int[]{positionX, positionY};
        } else {
            throw new IllegalArgumentException("Format de ligne de position invalide: " + line);
        }
    }
}