package com.peaches.customenchants.Commands;

import com.peaches.customenchants.main.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class Tinker implements CommandExecutor {
    private final File msg = new File("plugins//CustomEnchants//Messages.yml");
    private final YamlConfiguration messages = YamlConfiguration.loadConfiguration(this.msg);

    public Tinker() {
    }

    public boolean onCommand(CommandSender cs, Command cmd, String String, String[] args) {
        if ((cs instanceof Player)) {
            Player p = (Player) cs;
            p.openInventory(Utils.Tinkerer());
        }
        return false;
    }
}
