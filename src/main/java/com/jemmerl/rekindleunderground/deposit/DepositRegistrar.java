package com.jemmerl.rekindleunderground.deposit;

import java.util.ArrayList;

public class DepositRegistrar {
    private static ArrayList<IDeposit> oreDeposits;

    public DepositRegistrar() {
        oreDeposits = new ArrayList<>();
    }

    public boolean addDeposit(IDeposit deposit) {
        return oreDeposits.add(deposit);
    }

    public static ArrayList<IDeposit> getDeposits() {
        return oreDeposits;
    }

    public void clearDeposits() {
        oreDeposits.clear();
    }

}
