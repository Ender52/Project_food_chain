package cz.cvut.fel.omo;

import cz.cvut.fel.omo.parties.Party;
import cz.cvut.fel.omo.parties.bakery.Bakery;
import cz.cvut.fel.omo.parties.milk.MilkFarmer;
import cz.cvut.fel.omo.parties.oranges.OrangeFarmer;
import cz.cvut.fel.omo.parties.shop.Shop;
import cz.cvut.fel.omo.parties.wheat.WheatFarmer;

import java.util.ArrayList;
import java.util.List;

public class EcoSystem {
    private BlockChain blockChain;
    private int day = 0;
    private List<Party> parties = new ArrayList<>();
    private int partyId = 0;
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
            if (!blockChain.isSequred()) {
                System.err.println("BlockChain is not sequred");
                break;
            }
            for (Party p : parties) {
                p.checkRequestsToMe();
                p.work();
            }
            blockChain.sequre();
            day++;
        }
    }

    void createParty(char type, String name) {
        switch (type) {
            case 'B':
                parties.add(new Bakery(name, this, partyId++));
                break;
            case 'M':
                parties.add(new MilkFarmer(name, this, partyId++));
                break;
            case 'O':
                parties.add(new OrangeFarmer(name, this, partyId++));
                break;
            case 'S':
                parties.add(new Shop(name, this, partyId++));
                break;
            case 'W':
                parties.add(new WheatFarmer(name, this, partyId++));
                break;
        }
    }


}
