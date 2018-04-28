package com.peaches.customenchants.Support;

import com.peaches.customenchants.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Support {
    private static Main plugin;

    public Support(Main pl) {
        plugin = pl;
    }

    private final String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();

    private static boolean MVdWUpdater() {
        return Bukkit.getServer().getPluginManager().getPlugin("MVdWUpdater") != null;
    }

    public static boolean Vault() {
        return Bukkit.getServer().getPluginManager().getPlugin("Vault") != null;
    }

    public static boolean PAC() {
        return Bukkit.getServer().getPluginManager().getPlugin("PAC") != null;
    }

    public static boolean Spartan() {
        return Bukkit.getServer().getPluginManager().getPlugin("Spartan") != null;
    }

    public static boolean AAC() {
        return Bukkit.getServer().getPluginManager().getPlugin("AAC") != null;
    }

    private static boolean NCP() {
        return Bukkit.getServer().getPluginManager().getPlugin("NoCheatPlus") != null;
    }

    private static boolean Kingdoms() {
        return Bukkit.getServer().getPluginManager().getPlugin("Kingdoms") != null;
    }

    private static boolean WorldEdit() {
        return Bukkit.getServer().getPluginManager().getPlugin("WorldEdit") != null;
    }

    private static boolean WorldGuard() {
        return Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") != null;
    }

    private static boolean Factions() {
        return Bukkit.getServer().getPluginManager().getPlugin("Factions") != null;
    }

    public void Update() {
        if (MVdWUpdater()) {
            Updater.Update(path);
        }
    }

    public static boolean allowsPVP(Location loc) {
        return !WorldGuard() || !WorldEdit() || WorldGuard.allowsPVP(loc);
    }

    public static boolean allowsBreak(Location loc, Player p) {
        return !WorldGuard() || !WorldEdit() || WorldGuard.allowsBreak(loc, p);
    }

    public static boolean isFriendly(Entity P, Entity O) {
        if (((P instanceof Player)) && ((O instanceof Player))) {
            Player player = (Player) P;
            Player other = (Player) O;
            if (Kingdoms()) {
                return KingdomsSupport.isFriendly(player, other);
            }
            if (Factions()) {
                Plugin factions = Bukkit.getServer().getPluginManager().getPlugin("Factions");
                if ((factions.getDescription().getAuthors().contains("drtshock"))
                        && (FactionsUUID.isFriendly(player, other))) {
                    return true;
                }
                if (factions.getDescription().getWebsite() != null) {

                    return (factions.getDescription().getWebsite()
                            .equalsIgnoreCase("https://www.massivecraft.com/factions"))
                            && (FactionsSupport.isFriendly(player, other));
                }
            }
        }

        return false;
    }

    public static boolean canBreakBlock(@Nullable Player player, @NotNull Block block) {
            if (Kingdoms()) {
                return KingdomsSupport.canBreakBlock(player, block);
            }
            if (Factions()) {
                Plugin factions = Bukkit.getServer().getPluginManager().getPlugin("Factions");
                if (player != null) {
                    if ((factions.getDescription().getAuthors().contains("drtshock"))) {
                        return FactionsUUID.canBreakBlock(player, block);
                    }
                    if (factions.getDescription().getWebsite() != null) {
                        if ((factions.getDescription().getWebsite()
                                .equalsIgnoreCase("https://www.massivecraft.com/factions"))) {
                            return FactionsSupport.canBreakBlock(player, block);
                        }
                    }
                }
            }
            return true;
        }

        public static boolean inTerritory (Player player){
                if (Kingdoms()) {
                    return KingdomsSupport.inTerritory(player);
                }
                if (Factions()) {
                    Plugin factions = Bukkit.getServer().getPluginManager().getPlugin("Factions");
                    if (factions.getDescription().getAuthors().contains("drtshock")) {
                        return FactionsUUID.inTerritory(player);
                    }

                    if ((factions.getDescription().getWebsite() != null)
                            && (factions.getDescription().getAuthors().contains("Cayorion"))
                            && (FactionsSupport.inTerritory(player))) {
                        return true;
                    }
                }
            return false;
        }

        public static void RemoveExemption (@NotNull Player p){
            if (NCP()) {
                NoCheatPlusSupport.RemovePlayer(p);
            }
            if (AAC()) {
                AACSupport.UnExemptPlayer(p);
            }
            if (Spartan()) {
                SpartanSupport.UnExemptPlayer(p);
            }
            if (PAC()) {
                PACSupport.UnExemptPlayer(p);
            }
        }

        public static void AddExemption (@NotNull Player p){
            if (NCP()) {
                NoCheatPlusSupport.addPlayer(p);
            }
            if (AAC()) {
                AACSupport.ExemptPlayer(p);
            }
            if (Spartan()) {
                SpartanSupport.ExemptPlayer(p);
            }
            if (PAC()) {
                PACSupport.ExemptPlayer(p);
            }
        }
    }