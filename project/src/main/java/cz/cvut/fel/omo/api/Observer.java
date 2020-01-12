package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.transactions.Request;

/**
 * Observer of Channel to update information about Requests
 */
public interface Observer {
    /**
     * updates information about requests in channel
     *
     * @param request of last action in channel
     * @param added   if true, removed if false
     */
    void update(Request request, boolean added);
}
