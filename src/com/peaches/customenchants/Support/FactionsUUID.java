package com.peaches.customenchants.Support;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Relation;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class FactionsUUID
{
    public static boolean isEnabled()
    {
        if (org.bukkit.Bukkit.getServer().getPluginManager().isPluginEnabled("Factions")) {
            return true;
        }
        return false;
    }

    public static boolean isFriendly(Player player, Player other)
    {
        Faction p = FPlayers.getInstance().getByPlayer(player).getFaction();
        Faction o = FPlayers.getInstance().getByPlayer(other).getFaction();
        Relation r = FPlayers.getInstance().getByPlayer(player).getRelationTo(FPlayers.getInstance().getByPlayer(other));
        if (ChatColor.stripColor(o.getTag()).equalsIgnoreCase("Wilderness")) {
            return false;
        }
        if (p == o) {
            return true;
        }
        if (!r.isAlly()) {
            return false;
        }
        if (r.isAlly()) {
            return true;
        }
        return false;
    }

    public static boolean inTerritory(Player P)
    {
        if (ChatColor.stripColor(FPlayers.getInstance().getByPlayer(P).getFaction().getTag()).equalsIgnoreCase("Wilderness")) {
            return false;
        }
        if (ChatColor.stripColor(FPlayers.getInstance().getByPlayer(P).getFaction().getTag()).equalsIgnoreCase("SafeZone")) {
            return true;
        }
        if (FPlayers.getInstance().getByPlayer(P).isInOwnTerritory()) {
            return true;
        }
        if (FPlayers.getInstance().getByPlayer(P).isInAllyTerritory()) {
            return true;
        }
        return false;
    }

    public static boolean canBreakBlock(Player player, Block block)
    {
        Faction P = FPlayers.getInstance().getByPlayer(player).getFaction();
        FLocation loc = new FLocation(block.getLocation());
        Faction B = Board.getInstance().getFactionAt(loc);
        if ((ChatColor.stripColor(B.getTag()).equalsIgnoreCase("Wilderness")) || (P == B)) {
            return true;
        }
        return false;
    }
}


