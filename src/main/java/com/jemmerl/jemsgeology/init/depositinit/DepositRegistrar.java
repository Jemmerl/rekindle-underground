package com.jemmerl.jemsgeology.init.depositinit;

import com.jemmerl.jemsgeology.geology.deposits.IEnqueuedDeposit;
import com.jemmerl.jemsgeology.geology.deposits.instances.ConstantScatterDeposit;
import com.jemmerl.jemsgeology.geology.deposits.instances.PlacerDeposit;

import java.util.HashMap;

public class DepositRegistrar {
    private static HashMap<String, IEnqueuedDeposit> oreDeposits;
    private static HashMap<String, PlacerDeposit> placerDeposits;
    private static HashMap<String, ConstantScatterDeposit> constScatterDeposits;

    public DepositRegistrar() {
        oreDeposits = new HashMap<>(); // Holds all enqueued deposits
        placerDeposits = new HashMap<>();
        constScatterDeposits = new HashMap<>();
    }

    public void addOreDeposit(String nameKey, IEnqueuedDeposit deposit) {
        oreDeposits.put(nameKey, deposit);
    }

    public void addPlacerDeposit(String nameKey, PlacerDeposit placerDeposit) {
        placerDeposits.put(nameKey, placerDeposit);
    }

    public void addConstScatterDeposit(String nameKey, ConstantScatterDeposit constScatterDeposit) {
        constScatterDeposits.put(nameKey, constScatterDeposit);
    }

    public static HashMap<String, IEnqueuedDeposit> getOreDeposits() {
        return oreDeposits;
    }
    public static HashMap<String, PlacerDeposit> getPlacerDeposits() {
        return placerDeposits;
    }
    public static HashMap<String, ConstantScatterDeposit> getConstScatterDeposits() {
        return constScatterDeposits;
    }

    public void clearDeposits() {
        oreDeposits.clear();
        placerDeposits.clear();
        constScatterDeposits.clear();
    }

}
