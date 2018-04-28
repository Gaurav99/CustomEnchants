package com.peaches.customenchants.events;

import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerItemDamagetake implements Listener {
    private static Main plugin;

    public PlayerItemDamagetake(Main pl) {
        plugin = pl;
    }

    public void onDmg(@NotNull PlayerItemDamageEvent e) {
        ItemStack item = e.getItem();
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Hardened"); counter++) {
            if (Utils.hasenchant("Hardened " + Utils.convertPower(counter), item) && plugin.getConfig().getBoolean("Enabled.Hardened" + Utils.convertPower(counter))) {
                e.setDamage(e.getDamage() - (20 * counter));
            }
        }
        if (plugin.containspenetrate(e.getPlayer().getName())) {
            e.setDamage(e.getDamage() + (20 * plugin.getpenetrate(e.getPlayer().getName())));
            plugin.removepenetrate(e.getPlayer().getName());
        }
    }

}
