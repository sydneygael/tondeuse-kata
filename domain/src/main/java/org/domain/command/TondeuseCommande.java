package org.domain.command;

/**
 * interface représentant une commande
 * @FunctionalInterface pour signifier qu'elle n'a qu'une méthode et peut être utilisée en lambda
 */
@FunctionalInterface
public interface TondeuseCommande {
    String execute();
}
