package cz.cvut.fel.omo.api;

/**
 * Factory for Party
 */
public interface PartyFactory {
    /**
     * Creates instance of Party class
     *
     * @param type of Party
     * @param name of Party
     * @return created Party
     */
    Party createParty(char type, String name);
}
