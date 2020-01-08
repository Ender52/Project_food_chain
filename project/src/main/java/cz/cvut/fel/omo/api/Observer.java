package cz.cvut.fel.omo.api;

import cz.cvut.fel.omo.transactions.Request;

public interface Observer {
    void update(Request request, boolean added);
}
