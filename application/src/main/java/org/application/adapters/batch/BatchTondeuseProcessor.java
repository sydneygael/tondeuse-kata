package org.application.adapters.batch;

import lombok.AllArgsConstructor;
import org.domain.enums.CommandEnum;
import org.domain.enums.OrientationEnum;
import org.domain.exceptions.CommandNotFoundException;
import org.domain.exceptions.OrientationNotFoundException;
import org.domain.exceptions.UnknownCommandException;
import org.domain.models.entities.SurfaceRectangle;
import org.domain.models.entities.Tondeuse;
import org.domain.models.valueobjects.Position;
import org.domain.ports.input.MoveTondeusePort;
import org.domain.service.TondeuseService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BatchTondeuseProcessor implements ItemProcessor<String, String> {

    private static final Pattern COORDINATE_PATTERN = Pattern.compile("(\\d+) (\\d+) (N|E|W|S)");
    private static final Pattern DIMENTIONS_PATTERN = Pattern.compile("(\\d+) (\\d+)");
    private static final Pattern INSTRUCTIONS_PATTERN = Pattern.compile("[ADG]+");

    private final TondeuseService tondeuseService;

    @Override
    public String process(String input) throws Exception {
        Iterator<String> linesIterator = input.lines().iterator();

        // Traitement de la première ligne pour obtenir les rectangleDimentions de la surface
        var positionRectangleLine = linesIterator.next();
        var rectangleDimentions = extractSurfaceDimentions(positionRectangleLine);
        var surface = new SurfaceRectangle(Position.of(0, 0), rectangleDimentions[0], rectangleDimentions[1]);

        List<MoveTondeusePort.TondeuseMoveRequest> requests = new ArrayList<>();
        var index = 1;

        while (linesIterator.hasNext()) {
            String coordinateLine = linesIterator.next();
            if (isCoordinate(coordinateLine)) {
                Tondeuse tondeuse = createTondeuse(coordinateLine, index);
                index++;
                // s'assurer qu'il y a une prochaine ligne pour les instructions
                if (!linesIterator.hasNext()) {
                    throw new IllegalArgumentException("Aucune instruction trouvée pour la tondeuse.");
                }

                String instructionsLine = linesIterator.next();
                List<CommandEnum> commands = extractCommands(instructionsLine);

                requests.add(new MoveTondeusePort.TondeuseMoveRequest(commands, tondeuse, surface));
            }
        }

        if (requests.isEmpty()) {
            throw new IllegalArgumentException("Aucune tondeuse trouvée.");
        }

        return requests.stream().map(request -> {
            try {
                return tondeuseService.handle(request);
            } catch (UnknownCommandException e) {
                return "";
            }
        })
                .collect(Collectors.joining(" "));
    }

    private boolean isCoordinate(String line) {
        return COORDINATE_PATTERN.matcher(line).matches();
    }

    private int[] extractSurfaceDimentions(String line) {
        Matcher matcher = DIMENTIONS_PATTERN.matcher(line);
        if (matcher.matches()) {
            int positionX = Integer.parseInt(matcher.group(1));
            int positionY = Integer.parseInt(matcher.group(2));
            return new int[]{positionX, positionY};
        } else {
            throw new IllegalArgumentException("Format de ligne de position invalide: " + line);
        }
    }

    private Tondeuse createTondeuse(String coordinateLine, int index) throws OrientationNotFoundException {
        Matcher matcher = COORDINATE_PATTERN.matcher(coordinateLine);
        if (matcher.matches()) {
            int positionX = Integer.parseInt(matcher.group(1));
            int positionY = Integer.parseInt(matcher.group(2));
            var position = Position.of(positionX, positionY);
            var orientation = OrientationEnum.of(String.valueOf(matcher.group(3)));
            return new Tondeuse(index, position, orientation);
        } else {
            throw new IllegalArgumentException("Format de ligne de position invalide: " + coordinateLine);
        }
    }

    private List<CommandEnum> extractCommands(String line) throws CommandNotFoundException {
        Matcher matcher = INSTRUCTIONS_PATTERN.matcher(line);
        if (matcher.find()) {
            String instructions = matcher.group();
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

