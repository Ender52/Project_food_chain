package cz.cvut.fel.omo;

import cz.cvut.fel.omo.parties.Party;

import java.util.ArrayList;
import java.util.List;

public class EcoSystem {
    private BlockChain blockChain;
    private List<Party> parties = new ArrayList<>();

    public EcoSystem(BlockChain bc, List<Party> parties) {
        blockChain = bc;
        this.parties = parties;
    }

    public void start(int steps) {
        for (int i = 0; i < steps; ++i) {
            for (Party p : parties) {
                p.checkRequestsToMe();
                p.work();
            }
        }
    }
}
