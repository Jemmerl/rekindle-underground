package com.jemmerl.rekindleunderground.deposit;

import java.util.HashMap;

public class DepositRegistrar {
    private static HashMap<String, IDeposit> oreDeposits;

    public DepositRegistrar() {
        oreDeposits = new HashMap<>();
    }

    public void addDeposit(String nameKey, IDeposit deposit) {
        oreDeposits.put(nameKey, deposit);
    }

    public static HashMap<String, IDeposit> getDeposits() {
        return oreDeposits;
    }

    public void clearDeposits() {
        oreDeposits.clear();
    }

}
