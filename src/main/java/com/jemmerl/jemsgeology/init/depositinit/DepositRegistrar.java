package com.jemmerl.jemsgeology.init.depositinit;

import com.jemmerl.jemsgeology.geology.deposits.IEnqueuedDeposit;
import com.jemmerl.jemsgeology.geology.deposits.instances.ConstantScatterDeposit;
import com.jemmerl.jemsgeology.geology.deposits.instances.PlacerDeposit;

import java.util.HashMap;

public class DepositRegistrar {
    private static HashMap<String, IEnqueuedDeposit> oreDeposits;
    private static HashMap<String, PlacerDeposit> placerDeposits;
    private static HashMap<String, ConstantScatterDeposit> constScatterDeposits;
    private static HashMap<String, IEnqueuedDeposit> utilDeposits;

    public DepositRegistrar() {
        oreDeposits = new HashMap<>();
        placerDeposits = new HashMap<>();
        constScatterDeposits = new HashMap<>();
        utilDeposits = new HashMap<>();
    }

    public void addEnqueuedDeposit(String nameKey, IEnqueuedDeposit deposit) {
        oreDeposits.put(nameKey, deposit);
    }

    public void addPlacerDeposit(String nameKey, PlacerDeposit placerDeposit) {
        placerDeposits.put(nameKey, placerDeposit);
    }

    public void addConstScatterDeposit(String nameKey, ConstantScatterDeposit constScatterDeposit) {
        constScatterDeposits.put(nameKey, constScatterDeposit);
    }

    public void addUtilDeposit(String nameKey, IEnqueuedDeposit utilDeposit) {
        utilDeposits.put(nameKey, utilDeposit);
    }

    public static HashMap<String, IEnqueuedDeposit> getEnqOreDeposits() { return oreDeposits; }
    public static HashMap<String, PlacerDeposit> getPlacerDeposits() { return placerDeposits; }
    public static HashMap<String, ConstantScatterDeposit> getConstScatterDeposits() { return constScatterDeposits; }
    public static HashMap<String, IEnqueuedDeposit> getUtilDeposits() { return utilDeposits; }

    public void clearDeposits() {
        oreDeposits.clear();
        placerDeposits.clear();
        constScatterDeposits.clear();
        utilDeposits.clear();
    }

}
