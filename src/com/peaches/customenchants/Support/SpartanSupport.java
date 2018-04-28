package com.peaches.customenchants.Support;

import me.vagdedes.spartan.api.PlayerViolationEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SpartanSupport implements Listener {
    private static final ArrayList<String> Spartan = new ArrayList<>();

    public static void UnExemptPlayer(Player p) {
        Spartan.remove(p.getName());
    }

    public static void ExemptPlayer(Player p) {
        Spartan.add(p.getName());
    }

    @EventHandler
    public void onViolation(@NotNull PlayerViolationEvent e) {
        if (Spartan.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }


}
