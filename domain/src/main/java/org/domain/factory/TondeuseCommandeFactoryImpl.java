package org.domain.factory;

import org.domain.enums.CommandEnum;
import org.domain.models.entities.Surface;
import org.domain.models.entities.Tondeuse;
import org.domain.command.TondeuseCommande;
import org.domain.command.impl.AvancerCommande;
import org.domain.command.impl.PivoterDroiteCommande;
import org.domain.command.impl.PivoterGaucheCommande;

public class TondeuseCommandeFactoryImpl implements TondeuseCommandeFactory {
    private static final PivoterGaucheCommande PIVOTER_GAUCHE_COMMANDE = new PivoterGaucheCommande();
    private static final PivoterDroiteCommande PIVOTER_DROITE_COMMANDE = new PivoterDroiteCommande();
    private static final AvancerCommande AVANCER_COMMANDE = new AvancerCommande();

    @Override
    public TondeuseCommande getCommand(CommandEnum type, Tondeuse tondeuse, Surface surface) {
        switch (type) {
            case LEFT:
                PIVOTER_GAUCHE_COMMANDE.setTondeuse(tondeuse);
                return PIVOTER_GAUCHE_COMMANDE;
            case RIGHT:
                PIVOTER_DROITE_COMMANDE.setTondeuse(tondeuse);
                return PIVOTER_DROITE_COMMANDE;
            case ADVANCE:
                AVANCER_COMMANDE.setTondeuse(tondeuse);
                AVANCER_COMMANDE.setSurface(surface);
                return AVANCER_COMMANDE;
            default:
                throw new IllegalArgumentException("Type de commande inconnu : " + type);
        }
    }
}
