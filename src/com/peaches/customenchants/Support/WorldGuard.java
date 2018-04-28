package com.peaches.customenchants.Support;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

class WorldGuard {
    private static boolean isEnabled() {
        return Bukkit.getServer().getPluginManager().getPlugin("WorldGuard").isEnabled();
    }

    public static boolean allowsPVP(@NotNull Location loc) {
        if(isEnabled())return true;
        ApplicableRegionSet set = com.sk89q.worldguard.bukkit.WGBukkit.getPlugin().getRegionManager(loc.getWorld())
                .getApplicableRegions(loc);
        if (set.queryState(null, DefaultFlag.INVINCIBILITY) != StateFlag.State.ALLOW) {
            set.queryState(null, DefaultFlag.PVP);
        } else return false;
        return true;
    }

    public static boolean allowsBreak(Location loc, Player p) {
        if(isEnabled())return true;
        WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        return wg.canBuild(p, loc);
    }
}


