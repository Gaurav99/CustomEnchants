package com.peaches.customenchants.Support;

import me.themuhammed2188.pac.api.HackType;
import me.themuhammed2188.pac.api.PlayerViolationEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PACSupport implements Listener {
    private static final ArrayList<String> AAC = new ArrayList<>();

    public static void UnExemptPlayer(Player p) {
        AAC.remove(p.getName());
    }

    public static void ExemptPlayer(Player p) {
        AAC.add(p.getName());
    }

    @EventHandler
    public void onViolation(@NotNull PlayerViolationEvent e) {
        if (AAC.contains(e.getPlayer().getName())) {
            if (e.getHackType().equals(HackType.MORE_PACKETS) || e.getHackType().equals(HackType.IMPOSSIBLE_INTERACT)) {
                e.setCancelled(true);
            }
        }
    }
}
