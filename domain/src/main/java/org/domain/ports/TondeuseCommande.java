package org.domain.ports;

import org.domain.models.valueobjects.Position;

/**
 * interface représentant une commande
 * @FunctionalInterface pour signifier qu'elle n'a qu'une méthode et peut être utilisée en lambda
 */
@FunctionalInterface
public interface TondeuseCommande {
    String execute();
}
