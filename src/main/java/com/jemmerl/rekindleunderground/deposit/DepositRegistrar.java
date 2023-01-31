package com.jemmerl.rekindleunderground.deposit;

import com.jemmerl.rekindleunderground.deposit.generators.PlacerDeposit;

import java.util.HashMap;

public class DepositRegistrar {
    private static HashMap<String, IEnqueuedDeposit> oreDeposits;
    private static HashMap<String, IDeposit> placerDeposits;

    public DepositRegistrar() {
        oreDeposits = new HashMap<>();
        placerDeposits = new HashMap<>();
    }

    public void addOreDeposit(String nameKey, IEnqueuedDeposit deposit) {
        oreDeposits.put(nameKey, deposit);
    }

    public void addPlacerDeposit(String nameKey, IDeposit placerDeposit) {
        placerDeposits.put(nameKey, placerDeposit);
    }

    public static HashMap<String, IEnqueuedDeposit> getOreDeposits() {
        return oreDeposits;
    }
    public static HashMap<String, IDeposit> getPlacerDeposits() {
        return placerDeposits;
    }

    public void clearDeposits() {
        oreDeposits.clear();
        placerDeposits.clear();
    }

}
