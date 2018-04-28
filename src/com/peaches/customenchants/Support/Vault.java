package com.peaches.customenchants.Support;


import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class Vault {

    private static Economy economy;

    public static Double getbal(Player p){
        return economy.getBalance(p);
    }

    public static void removebal(Player p, Double amount){
        economy.withdrawPlayer(p, amount);
    }
    public static void addbal(Player p, Double amount){
        economy.withdrawPlayer(p, 0-amount);
    }

}
