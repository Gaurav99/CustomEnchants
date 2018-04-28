package com.peaches.customenchants.api;

import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class API implements Listener {

    private static Main plugin;
    private static final HandlerList handlers = new HandlerList();

    public API(Main pl) {
        plugin = pl;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }

    public void addEnchant(String enchant, String Type, String description) {
        Utils.type.put(enchant, Type);
        Utils.desc.put(enchant, description);
    }

    public static Boolean isEnchantEnabled(String Enchant) {
        return plugin.getConfig().getBoolean("Enabled."+Enchant);
    }

}
