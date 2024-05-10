package org.application.adapters.batch;

import lombok.AllArgsConstructor;
import org.application.ports.input.MoveTondeusePort;
import org.application.usescases.DeplacerTondeuse;
import org.domain.enums.CommandEnum;
import org.domain.enums.OrientationEnum;
import org.domain.exceptions.CommandNotFoundException;
import org.domain.exceptions.OrientationNotFoundException;
import org.domain.exceptions.UnknownCommandException;
import org.domain.models.entities.SurfaceRectangle;
import org.domain.models.entities.Tondeuse;
import org.domain.models.valueobjects.Position;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@AllArgsConstructor
public class BatchTondeuseProcessor implements ItemProcessor<String, String> {

    private static final Pattern COORDINATE_PATTERN = Pattern.compile("(\\d+) (\\d+) ([NEWS])");
    private static final Pattern DIMENTIONS_PATTERN = Pattern.compile("(\\d+) (\\d+)");
    private static final Pattern INSTRUCTIONS_PATTERN = Pattern.compile("[ADG]+");

    private final DeplacerTondeuse deplacerTondeuse;

    @Override
    public String process(String input) throws Exception {
        Iterator<String> linesIterator = input.lines().iterator();

        // Traitement de la première ligne pour obtenir les dimensions de la surface
        var lignePositionRectangle = linesIterator.next();
        var rectangleDimensions = extraireLesDimensionsDeLaSurface(lignePositionRectangle);
        var surface = new SurfaceRectangle(Position.of(0, 0, null), rectangleDimensions[0], rectangleDimensions[1]);

        List<MoveTondeusePort.DeplacerTondeuseRequete> requetes = new ArrayList<>();
        var index = 1;

        while (linesIterator.hasNext()) {
            var ligneCoordonnees = linesIterator.next();
            if (isCoordinate(ligneCoordonnees)) {
                var tondeuse = creerTondeuse(ligneCoordonnees, index);
                index++;
                // s'assurer qu'il y a une prochaine ligne pour les instructions
                if (!linesIterator.hasNext()) {
                    throw new IllegalArgumentException("Aucune instruction trouvée pour la tondeuse.");
                }

                var ligneInstructions = linesIterator.next();
                List<CommandEnum> commands = extractCommands(ligneInstructions);

                requetes.add(new MoveTondeusePort.DeplacerTondeuseRequete(commands, tondeuse, surface));
            }
        }

        if (requetes.isEmpty()) {
            throw new IllegalArgumentException("Aucune tondeuse trouvée.");
        }

        return requetes.stream().map(request -> {
                    try {
                        return deplacerTondeuse.handle(request);
                    } catch (UnknownCommandException e) {
                        return "";
                    }
                })
                .collect(Collectors.joining(" "));
    }

    private boolean isCoordinate(String line) {
        return COORDINATE_PATTERN.matcher(line).matches();
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

    private Tondeuse creerTondeuse(String coordinateLine, int index) throws OrientationNotFoundException {
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

    private List<CommandEnum> extractCommands(String line) throws CommandNotFoundException {
        Matcher matcher = INSTRUCTIONS_PATTERN.matcher(line);
        if (matcher.find()) {
            var instructions = matcher.group();
            List<CommandEnum> commands = new ArrayList<>();
            for (char instruction : instructions.toCharArray()) {
                commands.add(CommandEnum.of(String.valueOf(instruction)));
            }
            return commands;
        } else {
            throw new IllegalArgumentException("Aucune instruction trouvée dans la ligne: " + line);
        }
    }
}

