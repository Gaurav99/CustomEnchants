package com.peaches.customenchants.Commands;

import com.peaches.customenchants.Support.Support;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class CustomEnchants implements org.bukkit.command.CommandExecutor {
    private static Main plugin;
    private final File msg = new File("plugins//CustomEnchants//Messages.yml");
    private final YamlConfiguration messages = YamlConfiguration.loadConfiguration(msg);

    public CustomEnchants(Main pl) {
        plugin = pl;
    }

    public boolean onCommand(CommandSender cs, org.bukkit.command.Command cmd, String String, @NotNull String[] args) {
        if (args.length == 0) {
            if (Utils.Tier()) {
                if (cs instanceof Player) {
                    Player p = (Player) cs;
                    p.openInventory(Utils.showInventory(p));
                    return false;
                }
            }
        }
        if ((args.length == 1) && (args[0].equalsIgnoreCase("update"))) {
            if(cs.hasPermission("customenchants.update")){
                Support supp = new Support(plugin);
                supp.Update();
            }
        }
        if ((args.length == 1) && (args[0].equalsIgnoreCase("about"))) {
            cs.sendMessage(ChatColor.DARK_GRAY + "Plugin Name: " + ChatColor.GRAY + plugin.getName());
            cs.sendMessage(ChatColor.DARK_GRAY + "Plugin Version: " + ChatColor.GRAY
                    + plugin.getDescription().getVersion());
            cs.sendMessage(ChatColor.DARK_GRAY + "Plugin Author: " + ChatColor.GRAY + "Peaches_MLG");
            return false;
        }

        if ((args.length == 1) && (args[0].equalsIgnoreCase("list"))) {
            if ((cs instanceof Player)) {
                Player p = (Player) cs;
                p.openInventory(Utils.showList(1));
                return false;
            }

            return false;
        }
        java.text.DecimalFormat localDecimalFormat;
        if ((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {
            if (!cs.hasPermission("customenchants.reload")) {
                cs.sendMessage(Utils.prefix()
                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("NoPermission")));
                return false;
            }
            long l1 = System.currentTimeMillis();
            cs.sendMessage(Utils.prefix()
                    + ChatColor.translateAlternateColorCodes('&', this.messages.getString("StartReload")));
            plugin.reloadConfig();
            plugin.getConfig().options().copyDefaults(true);
            plugin.saveDefaultConfig();
            for (String Enchant : Utils.type.keySet()) {
                for (int counter = 0; counter <= plugin.getConfig().getInt("MaxPower." + Enchant); counter++) {
                    if (!plugin.getConfig().contains("Enabled." + Enchant + Utils.convertPower(counter))) {
                        plugin.getConfig().set("Enabled." + Enchant + Utils.convertPower(counter),
                                Boolean.TRUE);
                    }
                }
            }
            plugin.saveConfig();
            long l2 = System.currentTimeMillis() - l1;
            localDecimalFormat = new java.text.DecimalFormat("###.##");
            cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                    this.messages.getString("EndReload").replace("%ms%", localDecimalFormat.format(l2))));
            return false;
        }
        if (args.length == 2) {
            String arg1 = args[1];
            if (args[0].equalsIgnoreCase("enable")) {
                if (!cs.hasPermission("customenchants.enable")) {
                    cs.sendMessage(Utils.prefix()
                            + ChatColor.translateAlternateColorCodes('&', this.messages.getString("NoPermission")));
                    return false;
                }
                if (plugin.getConfig().get("Enabled." + args[1]) != null) {
                    plugin.getConfig().set("Enabled." + args[1], Boolean.TRUE);
                    plugin.saveConfig();
                    cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                            this.messages.getString("EnableEnchant").toLowerCase().replace("%enchant%", arg1)));
                    return false;
                }
                cs.sendMessage(Utils.prefix()
                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("UnknownEnchant")));
                return false;
            }
            if (args[0].equalsIgnoreCase("disable")) {
                if (!cs.hasPermission("customenchants.disable")) {
                    cs.sendMessage(Utils.prefix()
                            + ChatColor.translateAlternateColorCodes('&', this.messages.getString("NoPermission")));
                    return false;
                }
                if (plugin.getConfig().get("Enabled." + args[1]) != null) {
                    plugin.getConfig().set("Enabled." + args[1], Boolean.FALSE);
                    plugin.saveConfig();
                    cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                            this.messages.getString("DisableEnchant").toLowerCase().replace("%enchant%", arg1)));
                    return false;
                }
                cs.sendMessage(Utils.prefix()
                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("UnknownEnchant")));
                return false;
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("give")) {
                if (!cs.hasPermission("customenchants.give")) {
                    cs.sendMessage(Utils.prefix()
                            + ChatColor.translateAlternateColorCodes('&', this.messages.getString("NoPermission")));
                    return false;
                }
                for (String Enchant : Utils.type.keySet()) {
                    if (plugin.getConfig().contains("Translate." + Enchant)) {
                        if (args[2].toLowerCase()
                                .contains(plugin.getConfig().getString("Translate." + Enchant).toLowerCase())) {
                            if (plugin.getConfig().contains("MaxPower." + Enchant)) {
                                for (int counter = 1; counter <= plugin.getConfig()
                                        .getInt("MaxPower." + Enchant); counter++) {
                                    if ((args[2].equalsIgnoreCase(plugin.getConfig().getString("Translate." + Enchant)
                                            + Utils.convertPower(counter)))
                                            || (args[2].equalsIgnoreCase(
                                            plugin.getConfig().getString("Translate." + Enchant)
                                                    + Integer.toString(counter)))) {
                                        if (!plugin.getConfig()
                                                .getBoolean("Enabled." + Enchant + Utils.convertPower(counter))) {
                                            if (plugin.getConfig()
                                                    .contains("Enabled." + Enchant + Utils.convertPower(counter))) {
                                                cs.sendMessage(
                                                        Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                                                this.messages.getString("EnchantDisabled").toLowerCase().replace(
                                                                        "%enchant%",
                                                                        Enchant + Utils.convertPower(counter))));
                                                return false;
                                            }
                                            plugin.getConfig().set("Enabled." + Enchant + Utils.convertPower(counter),
                                                    Boolean.TRUE);
                                            plugin.saveConfig();
                                            plugin.reloadConfig();
                                        }
                                        Player p = Bukkit.getServer().getPlayer(args[1]);
                                        if (p == null) {
                                            cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                                    this.messages.getString("UnknownPlayer")));
                                            return false;
                                        }
                                        if ((cs instanceof Player)) {
                                            cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                                    this.messages.getString("GivePlayerEnchant").toLowerCase()
                                                            .replace("%player%", args[1]).replace("%enchant%",
                                                            java.lang.String.valueOf(plugin
                                                                    .getConfig()
                                                                    .getString("Translate." +
                                                                            Enchant)) +
                                                                    Utils.convertPower(
                                                                            counter))));
                                        }
                                        if (Utils.hasOpenSlot(p.getInventory())) {
                                            p.getInventory()
                                                    .addItem(Utils.createCrystalItem(
                                                            plugin.getConfig().getString("Translate." + Enchant),
                                                            Utils.convertPower(counter),
                                                            Utils.type.get(Enchant)));
                                            return false;
                                        }
                                        p.getWorld().dropItem(p.getLocation(),
                                                Utils.createCrystalItem(
                                                        plugin.getConfig().getString("Translate." + Enchant),
                                                        Utils.convertPower(counter), Utils.type.get(Enchant)));
                                        return false;
                                    }
                                }
                            } else {
                                Player p = Bukkit.getServer().getPlayer(args[1]);
                                if (p == null) {
                                    cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                            this.messages.getString("UnknownPlayer")));
                                    return false;
                                }
                                if ((cs instanceof Player)) {
                                    cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                            this.messages.getString("GivePlayerEnchant").toLowerCase().replace("%player%", args[1])
                                                    .replace("%enchant%", java.lang.String.valueOf(plugin.getConfig()
                                                            .getString("Translate." +
                                                                    Enchant)) +
                                                            Utils.convertPower(1))));
                                }
                                if (Utils.hasOpenSlot(p.getInventory())) {
                                    p.getInventory()
                                            .addItem(Utils.createCrystalItem(
                                                    plugin.getConfig().getString("Translate." + Enchant),
                                                    Utils.convertPower(1), Utils.type.get(Enchant)));
                                    return false;
                                }
                                p.getWorld().dropItem(p.getLocation(),
                                        Utils.createCrystalItem(plugin.getConfig().getString("Translate." + Enchant),
                                                Utils.convertPower(1), Utils.type.get(Enchant)));
                                return false;
                            }
                        }
                    }
                    if (args[2].toLowerCase().contains(Enchant.toLowerCase())) {
                        if (plugin.getConfig().contains("MaxPower." + Enchant)) {
                            for (int counter = 1; counter <= plugin.getConfig()
                                    .getInt("MaxPower." + Enchant); counter++) {
                                if ((args[2].equalsIgnoreCase(Enchant + Utils.convertPower(counter)))
                                        || (args[2].equalsIgnoreCase(Enchant + Integer.toString(counter)))) {
                                    if (!plugin.getConfig()
                                            .getBoolean("Enabled." + Enchant + Utils.convertPower(counter))) {
                                        if (plugin.getConfig()
                                                .contains("Enabled." + Enchant + Utils.convertPower(counter))) {
                                            cs.sendMessage(
                                                    Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                                            this.messages.getString("EnchantDisabled").toLowerCase().replace(
                                                                    "%enchant%",
                                                                    Enchant + Utils.convertPower(counter))));
                                            return false;
                                        }
                                        plugin.getConfig().set("Enabled." + Enchant + Utils.convertPower(counter),
                                                Boolean.TRUE);
                                        plugin.saveConfig();
                                        plugin.reloadConfig();
                                    }
                                    Player p = Bukkit.getServer().getPlayer(args[1]);
                                    if (p == null) {
                                        cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                                this.messages.getString("UnknownPlayer")));
                                        return false;
                                    }
                                    if ((cs instanceof Player)) {
                                        cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                                this.messages.getString("GivePlayerEnchant").toLowerCase()
                                                        .replace("%player%", args[1]).replace("%enchant%",
                                                        java.lang.String.valueOf(plugin
                                                                .getConfig()
                                                                .getString("Translate." +
                                                                        Enchant)) +
                                                                Utils.convertPower(
                                                                        counter))));
                                    }
                                    if (Utils.hasOpenSlot(p.getInventory())) {
                                        p.getInventory()
                                                .addItem(Utils.createCrystalItem(
                                                        plugin.getConfig().getString("Translate." + Enchant),
                                                        Utils.convertPower(counter),
                                                        Utils.type.get(Enchant)));
                                        return false;
                                    }
                                    p.getWorld().dropItem(p.getLocation(),
                                            Utils.createCrystalItem(
                                                    plugin.getConfig().getString("Translate." + Enchant),
                                                    Utils.convertPower(counter), Utils.type.get(Enchant)));
                                    return false;
                                }
                            }
                        } else {
                            Player p = Bukkit.getServer().getPlayer(args[1]);
                            if (p == null) {
                                cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                        this.messages.getString("UnknownPlayer")));
                                return false;
                            }
                            if ((cs instanceof Player)) {
                                cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                        this.messages.getString("GivePlayerEnchant").toLowerCase().replace("%player%", args[1])
                                                .replace("%enchant%", java.lang.String.valueOf(plugin.getConfig()
                                                        .getString("Translate." +
                                                                Enchant)) +
                                                        Utils.convertPower(1))));
                            }
                            if (Utils.hasOpenSlot(p.getInventory())) {
                                p.getInventory()
                                        .addItem(Utils.createCrystalItem(
                                                plugin.getConfig().getString("Translate." + Enchant),
                                                Utils.convertPower(1), Utils.type.get(Enchant)));
                                return false;
                            }
                            p.getWorld().dropItem(p.getLocation(),
                                    Utils.createCrystalItem(plugin.getConfig().getString("Translate." + Enchant),
                                            Utils.convertPower(1), Utils.type.get(Enchant)));
                            return false;
                        }
                    }
                }
                java.io.File customenchants = new java.io.File("plugins//CustomEnchants//CustomEnchants.yml");
                YamlConfiguration enchants = YamlConfiguration.loadConfiguration(customenchants);
                if (enchants.getConfigurationSection("Enchantments") != null) {
                    for (String Enchant : enchants.getConfigurationSection("Enchantments").getKeys(false)) {
                        for (int counter = 0; counter <= enchants
                                .getInt("Enchantments." + Enchant + ".MaxPower"); counter++) {

                            if (args[2].equalsIgnoreCase(Enchant + Utils.convertPower(counter))) {
                                if (!enchants.getBoolean("Enchantments." + Enchant + ".Enabled")) {
                                    cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                            this.messages.getString("EnchantDisabled").toLowerCase().replace("%enchant%",
                                                    Enchant + Utils.convertPower(counter))));
                                    return false;
                                }
                                Player p = Bukkit.getServer().getPlayer(args[1]);
                                if (p == null) {
                                    cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                            this.messages.getString("UnknownPlayer")));
                                    return false;
                                }
                                if ((cs instanceof Player)) {
                                    cs.sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&',
                                            this.messages.getString("GivePlayerEnchant").toLowerCase()
                                                    .replace("%player%", args[1]).replace("%enchant%",
                                                    java.lang.String.valueOf(args[2]) +
                                                            " I")));
                                }
                                if (Utils.hasOpenSlot(p.getInventory())) {
                                    p.getInventory().addItem(Utils.createCrystalItem(Enchant,
                                            args[2].toLowerCase().replace(Enchant.toLowerCase(), ""),
                                            enchants.getString(
                                                    "Enchantments." + Enchant + ".Options.ItemsEnchantable")));
                                    return false;
                                }
                                p.getInventory().addItem(Utils.createCrystalItem(Enchant,
                                        args[2].toLowerCase().replace(Enchant.toLowerCase(), ""),
                                        enchants.getString(
                                                "Enchantments." + Enchant + ".Options.ItemsEnchantable")));
                                return false;
                            }
                        }
                    }
                }

                cs.sendMessage(Utils.prefix()
                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("UnknownEnchant")));
            }
        } else {
            if (Utils.Tier()) {
                cs.sendMessage(ChatColor.RED + "/ce" + ChatColor.RED + ": " + ChatColor.GRAY
                        + "Opens The Enchantment GUI");
            }
            cs.sendMessage(
                    ChatColor.RED + "/ce list" + ChatColor.RED + ": " + ChatColor.GRAY + "Lists The Enchants");
            if (cs.hasPermission("customenchants.reload")) {
                cs.sendMessage(ChatColor.RED + "/ce reload " + ChatColor.RED + ": " + ChatColor.GRAY
                        + "Reloads The Plugin");
            }
            cs.sendMessage(ChatColor.RED + "/ce update " + ChatColor.RED + ": " + ChatColor.GRAY
                    + "Updates The Plugin (Requires MvDW Updater)");
                cs.sendMessage(ChatColor.RED + "/ce about  " + ChatColor.RED + ": " + ChatColor.GRAY
                        + "tells you information about the plugin");

            if (cs.hasPermission("customenchants.give")) {
                cs.sendMessage(ChatColor.RED + "/ce give <name> <ce name>" + ChatColor.RED + ": " + ChatColor.GRAY
                        + "Give A Player An Enchant Crystal");
            }
            cs.sendMessage(ChatColor.RED + "/ce help " + ChatColor.RED + ": " + ChatColor.GRAY
                    + "View a helpful list of commands!");

            if (cs.hasPermission("customenchants.enable")) {
                cs.sendMessage(ChatColor.RED + "/ce enable <Enchant> " + ChatColor.RED + ": " + ChatColor.GRAY
                        + "Enable an enchant!");
            }
            if (cs.hasPermission("customenchants.disable")) {
                cs.sendMessage(ChatColor.RED + "/ce disable <Enchant> " + ChatColor.RED + ": " + ChatColor.GRAY
                        + "Disable an enchant!");
            }
            cs.sendMessage(
                    ChatColor.RED + "/Gkits " + ChatColor.RED + ": " + ChatColor.GRAY + "Opens The Gkits GUI!");
            if (cs.hasPermission("customenchants.gkits.reset")) {
                cs.sendMessage(ChatColor.RED + "/Gkits Reset <Name> <Kit> " + ChatColor.RED + ": " + ChatColor.GRAY
                        + "Reset a kit cooldown for a player");
            }
            cs.sendMessage(
                    ChatColor.RED + "/Tinkerer " + ChatColor.RED + ": " + ChatColor.GRAY + "Opens The Tinkerer GUI!");

        }
        return false;
    }
}
