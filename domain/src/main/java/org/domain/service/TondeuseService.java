package org.domain.service;

import org.domain.command.impl.AvancerCommande;
import org.domain.command.impl.PivoterDroiteCommande;
import org.domain.command.impl.PivoterGaucheCommande;
import org.domain.enums.CommandEnum;
import org.domain.models.entities.SurfaceRectangle;
import org.domain.models.entities.Tondeuse;

import java.util.List;

public class TondeuseService {


    public String deplacerTondeuse(List<CommandEnum> commands, Tondeuse tondeuse, SurfaceRectangle surface) {
        var result = "";
        for (CommandEnum commandEnum : commands) {
            switch (commandEnum) {
                case ADVANCE -> result = new AvancerCommande(tondeuse, surface).execute();
                case LEFT -> result = new PivoterGaucheCommande(tondeuse).execute();
                case RIGHT -> result = new PivoterDroiteCommande(tondeuse).execute();
            }
        }

        return result;
    }
}
