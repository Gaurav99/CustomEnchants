package com.peaches.customenchants.events;

import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerFoodChangeEvent implements org.bukkit.event.Listener {
    private static Main plugin;

    public PlayerFoodChangeEvent(Main pl) {
        plugin = pl;
    }

    @EventHandler
    public void onFoodChange(@NotNull FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        if ((p != null)) {
            if (p.getInventory().getHelmet() == null) {
                return;
            }
            if (p.getInventory().getHelmet().getItemMeta() == null) {
                return;
            }
            if (p.getInventory().getHelmet().getItemMeta().getLore() == null) {
                return;
            }
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Replenish") + " I", p.getInventory().getHelmet()) && plugin.getConfig().getBoolean("Enabled.ReplenishI")) {
                e.setCancelled(true);
                if (e.getFoodLevel() > p.getFoodLevel()) {
                    e.setCancelled(false);
                    return;
                }
                if (p.getFoodLevel() == 20) {
                    return;
                }
                p.setFoodLevel(p.getFoodLevel() + 1);
            }
        }
    }
}


