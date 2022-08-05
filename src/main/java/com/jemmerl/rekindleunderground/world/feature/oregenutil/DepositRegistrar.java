package com.jemmerl.rekindleunderground.world.feature.oregenutil;

import java.util.ArrayList;
import java.util.SortedMap;

public class DepositRegistrar {
    private ArrayList<IDeposit> oreDeposits;
    //private SortedMap<String, > oreFeatRegistry;

    public DepositRegistrar() {
        this.oreDeposits = new ArrayList<>();
    }

    public boolean addDeposit(IDeposit deposit) {
        return this.oreDeposits.add(deposit);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<IDeposit> getDeposits() {
        return ((ArrayList<IDeposit>) this.oreDeposits.clone());
    }

    public void clearDeposits() {
        this.oreDeposits.clear();
    }

}
