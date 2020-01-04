package cz.cvut.fel.omo;

import cz.cvut.fel.omo.parties.Party;

import java.util.ArrayList;
import java.util.List;

public class EcoSystem {
    private BlockChain blockChain;
    private int day = 0;
    private List<Party> parties = new ArrayList<>();

    public EcoSystem(BlockChain bc) {
        blockChain = bc;
    }

    public int getDay() {
        return day;
    }

    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> parties) {
        this.parties = parties;
    }

    public BlockChain getBlockChain() {
        return blockChain;
    }

    public void start(int steps) {
        for (int i = 0; i < steps; ++i) {
            for (Party p : parties) {
                p.checkRequestsToMe();
                p.work();
            }
            day++;
        }
    }
}
