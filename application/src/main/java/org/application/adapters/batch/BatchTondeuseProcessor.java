package org.application.adapters.batch;

import lombok.AllArgsConstructor;
import org.application.ports.input.MoveTondeusePort;
import org.application.usescases.DeplacerTondeuse;
import org.domain.enums.CommandEnum;
import org.domain.exceptions.CommandNotFoundException;
import org.domain.exceptions.UnknownCommandException;
import org.domain.factory.TondeuseFactory;
import org.domain.models.entities.SurfaceRectangle;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static org.domain.factory.TondeuseFactory.COORDINATE_PATTERN;
import static org.domain.factory.TondeuseFactory.INSTRUCTIONS_PATTERN;


@AllArgsConstructor
public class BatchTondeuseProcessor implements ItemProcessor<String, String> {

    private final DeplacerTondeuse deplacerTondeuse;
    private final TondeuseFactory tondeuseFactory = new TondeuseFactory();

    @Override
    public String process(String input) throws Exception {
        Iterator<String> linesIterator = input.lines().iterator();

        // Traitement de la première ligne pour obtenir les dimensions de la surface
        var lignePositionRectangle = linesIterator.next();
        var surface = (SurfaceRectangle) tondeuseFactory.creerSurfaceRectangle(lignePositionRectangle);
        List<MoveTondeusePort.DeplacerTondeuseRequete> requetes = new ArrayList<>();
        var index = 1;

        while (linesIterator.hasNext()) {
            var ligneCoordonnees = linesIterator.next();
            if (isCoordinate(ligneCoordonnees)) {
                var tondeuse = tondeuseFactory.creerTondeuse(ligneCoordonnees, index);
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
                        return deplacerTondeuse.traiter(request);
                    } catch (UnknownCommandException e) {
                        return "";
                    }
                })
                .collect(Collectors.joining(" "));
    }

    private boolean isCoordinate(String line) {
        return COORDINATE_PATTERN.matcher(line).matches();
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

