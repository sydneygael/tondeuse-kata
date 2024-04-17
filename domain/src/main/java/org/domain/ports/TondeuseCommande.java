package org.domain.ports;

/**
 * interface représentant une commande
 * @FunctionalInterface pour signifier qu'elle n'a qu'une méthode et peut être utilisée en lambda
 */
@FunctionalInterface
public interface TondeuseCommande {
    boolean execute();
}
