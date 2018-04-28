package com.peaches.customenchants.events;

import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerFish implements Listener {
    private static Main plugin;

    public PlayerFish(Main pl) {
        plugin = pl;
    }

    @EventHandler
    public void onfish(@NotNull PlayerFishEvent e) {
        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            if ((e.getCaught() instanceof Item)) {
                ItemStack item = ((Item) e.getCaught()).getItemStack();
                if (Utils.hasenchant(plugin.getConfig().getString("Translate.Cook") + " I",
                        e.getPlayer().getItemInHand()) && plugin.getConfig().getBoolean("Enabled.CookI")) {
                    item.setType(Material.COOKED_FISH);
                }
                for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Catch"); counter++) {
                    if (Utils.hasenchant(plugin.getConfig().getString("Translate.Catch") + " " + Utils.convertPower(counter),
                            e.getPlayer().getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Catch" + Utils.convertPower(counter))) {
                        item.setAmount(counter + 1);

                    }
                }
                ((Item) e.getCaught()).setItemStack(item);
            }
        }
    }

}
