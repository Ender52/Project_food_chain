package cz.cvut.fel.omo.api;

/**
 * Subject in Observer pattern
 * for Channel to notify Parties
 */
public interface Subject {
    /**
     * Add an observer
     *
     * @param observer
     */
    void addObserver(Observer observer);

    /**
     * Remove observer
     * @param observer
     */
    void removeObserver(Observer observer);

    /**
     * notify all Observers sending them information about last request change
     */
    void notifyAllObservers();
}
