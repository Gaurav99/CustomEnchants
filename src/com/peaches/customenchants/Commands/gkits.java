package com.peaches.customenchants.Commands;

import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class gkits implements CommandExecutor {
    private static Main plugin;
    private final File msg = new File("plugins//CustomEnchants//Messages.yml");
    private final YamlConfiguration messages = YamlConfiguration.loadConfiguration(this.msg);

    public gkits(Main pl) {
        plugin = pl;
    }

    public boolean onCommand(@NotNull CommandSender cs, Command cmd, String String, @NotNull String[] args) {
        if ((args.length == 3) && (args[0].equalsIgnoreCase("reset"))) {
            Player p = Bukkit.getPlayer(args[1]);
            if (p.hasPermission("customenchants.gkits.reset")) {
                if (plugin.Gkits.containsKey(p.getName() + ":" + args[2])) {
                    plugin.Gkits.remove(p.getName() + ":" + args[2]);
                    cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&', this.messages
                            .getString("GkitReset").replace("%player%", p.getName()).replace("%name%", args[2])));
                    return false;

                } else {
                    File gkits = new File("plugins//CustomEnchants//GKits.yml");
                    YamlConfiguration gkit = YamlConfiguration.loadConfiguration(gkits);
                    boolean i = false;
                    for (String Key : gkit.getKeys(false)) {
                        if (Key.equals(args[2])) {
                            i = true;
                        }
                    }
                    if (i) {
                        cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                this.messages.getString("GkitNoCooldown").replace("%player%", p.getName()).replace("%name%", args[2])));
                        return false;
                    } else {
                        cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                this.messages.getString("UnknownGkit").replace("%name%", args[2])));
                        return false;
                    }
                }
            } else {
                cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                        this.messages.getString("NoPermission")));
                return false;
            }
        }

        if ((cs instanceof Player)) {
            Player p = (Player) cs;
            p.openInventory(Utils.Gkits(p));
        }
        return false;
    }
}
