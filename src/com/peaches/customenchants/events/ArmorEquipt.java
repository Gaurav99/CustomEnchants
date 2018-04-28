package com.peaches.customenchants.events;

import ca.thederpygolems.armorequip.ArmorEquipEvent;
import com.peaches.customenchants.Support.Support;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

public class ArmorEquipt implements org.bukkit.event.Listener {
    private static Main plugin;

    public ArmorEquipt(Main pl) {
        plugin = pl;
    }

    @SuppressWarnings("unchecked")
    @EventHandler
    public void onEquip(@NotNull ArmorEquipEvent e) {
        File msg = new File("plugins//CustomEnchants//Messages.yml");
        YamlConfiguration messages = YamlConfiguration.loadConfiguration(msg);
        Player p = e.getPlayer();
        ItemStack NewItem = e.getNewArmorPiece();
        if (NewItem == null) {
            return;
        }
        if (NewItem.getItemMeta() == null) {
            return;
        }
        if (NewItem.getItemMeta().getLore() == null) {
            return;
        }

        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Resistance"); counter++) {
            if (Utils.hasenchant(
                    plugin.getConfig().getString("Translate.Resistance") + " " + Utils.convertPower(counter),
                    NewItem)) {

                if (plugin.getConfig().getBoolean("Enabled.Resistance" + Utils.convertPower(counter))) {
                    p.addPotionEffect(
                            new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, -1 + counter, false));
                }
            }
        }
        if (Utils.hasenchant(plugin.getConfig().getString("Translate.Wings") + " I", NewItem)) {

            if (plugin.getConfig().getBoolean("Enabled.WingsI")) {
                if (Support.inTerritory(p)) {
                    plugin.addfly(p.getName());
                    p.setAllowFlight(true);
                    p.setFlying(true);
                    p.sendMessage(Utils.prefix()
                            + ChatColor.translateAlternateColorCodes('&', messages.getString("FlyEnabled")));
                }
            }
        }
        if (Utils.hasenchant(plugin.getConfig().getString("Translate.Frosty") + " I", NewItem)) {
            if (plugin.getConfig().getBoolean("Enabled.FrostyI")) {
                plugin.addsnow(p.getName());
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Speed"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Speed") + " " + Utils.convertPower(counter),
                    NewItem)) {

                if (plugin.getConfig().getBoolean("Enabled.Speed" + Utils.convertPower(counter))) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, -1 + counter, false));
                }
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Jump"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Jump") + " " + Utils.convertPower(counter),
                    NewItem)) {

                if (plugin.getConfig().getBoolean("Enabled.Jump" + Utils.convertPower(counter))) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, -1 + counter, false));
                }
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Rage"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Rage") + " " + Utils.convertPower(counter),
                    NewItem)) {

                if (plugin.getConfig().getBoolean("Enabled.Rage" + Utils.convertPower(counter))) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, -1 + counter, false));
                }
            }
        }
        if (Utils.hasenchant(plugin.getConfig().getString("Translate.Visionary") + " I", NewItem)) {

            if (plugin.getConfig().getBoolean("Enabled.VisionaryI")) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0, false));
            }
        }
        if (Utils.hasenchant(plugin.getConfig().getString("Translate.Netherskin") + " I", NewItem)) {

            if (plugin.getConfig().getBoolean("Enabled.NetherskinI")) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999, 0, false));
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Jacket"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Jacket") + " " + Utils.convertPower(counter),
                    NewItem)) {

                if (plugin.getConfig().getBoolean("Enabled.Jacket" + Utils.convertPower(counter))) {
                    p.setMaxHealth(p.getMaxHealth() + counter);
                }
            }
        }
        if (Utils.hasenchant(plugin.getConfig().getString("Translate.Aquatic") + " I", NewItem)) {

            if (plugin.getConfig().getBoolean("Enabled.AquaticI")) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 999999, 5, false));
            }
        }
        java.io.File customenchants = new java.io.File("plugins//CustomEnchants//CustomEnchants.yml");
        YamlConfiguration enchants = YamlConfiguration.loadConfiguration(customenchants);
        if (enchants.getConfigurationSection("Enchantments") != null) {
            for (String Enchant : enchants.getConfigurationSection("Enchantments").getKeys(false)) {
                for (int counter = 0; counter <= enchants.getInt("Enchantments." + Enchant + ".MaxPower"); counter++) {
                    if (Utils.hasenchant(Enchant + " " + Utils.convertPower(counter), NewItem)) {
                        ArrayList<String> Potions = (ArrayList<String>) enchants
                                .getList("Enchantments." + Enchant + ".Options.PotionEffects");
                        for (String Potion : Potions) {
                            if (PotionEffectType.getByName(Potion) != null) {
                                p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(Potion),
                                        enchants.getInt("Enchantments." + Enchant + ".Options.PotionTime") * 20,
                                        -1 + counter
                                                * enchants.getInt("Enchantments." + Enchant + ".Options.PowerIncrease")
                                                + (enchants.getInt("Enchantments." + Enchant + ".StartingPower") - 1),
                                        false));
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @EventHandler
    public void onDequipt(@NotNull ArmorEquipEvent e) {
        File msg = new File("plugins//CustomEnchants//Messages.yml");
        YamlConfiguration messages = YamlConfiguration.loadConfiguration(msg);
        Player p = e.getPlayer();
        ItemStack OldItem = e.getOldArmorPiece();
        if (OldItem == null) {
            return;
        }
        if (OldItem.getItemMeta() == null) {
            return;
        }
        if (OldItem.getItemMeta().getLore() == null) {
            return;
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Resistance"); counter++) {
            if (Utils.hasenchant(
                    plugin.getConfig().getString("Translate.Resistance") + " " + Utils.convertPower(counter),
                    OldItem)) {
                if (p.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                    p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                }
            }
        }
        if (Utils.hasenchant(plugin.getConfig().getString("Translate.Wings") + " I", OldItem)) {
            if (plugin.containsfly(p.getName())) {
                plugin.removefly(p.getName());
                p.setAllowFlight(false);
                p.setFlying(false);
                p.sendMessage(Utils.prefix()
                        + ChatColor.translateAlternateColorCodes('&', messages.getString("FlyDisabled")));
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Speed"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Speed") + " " + Utils.convertPower(counter),
                    OldItem)) {
                if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                    p.removePotionEffect(PotionEffectType.SPEED);
                }
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Jump"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Jump") + " " + Utils.convertPower(counter),
                    OldItem)) {
                if (p.hasPotionEffect(PotionEffectType.JUMP)) {
                    p.removePotionEffect(PotionEffectType.JUMP);
                }
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Rage"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Rage") + " " + Utils.convertPower(counter),
                    OldItem)) {

                if (p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                    p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                }
            }
        }
        if (Utils.hasenchant(plugin.getConfig().getString("Translate.Frosty") + " I", OldItem)) {
            plugin.removensnow(p.getName());
        }
        if (Utils.hasenchant(plugin.getConfig().getString("Translate.Visionary") + " I", OldItem)) {
            if (p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }
        }
        if (Utils.hasenchant(plugin.getConfig().getString("Translate.Netherskin") + " I", OldItem)) {
            if (p.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Jacket"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Jacket") + " " + Utils.convertPower(counter),
                    OldItem)) {
                if (p.getMaxHealth() - counter > 0) {

                    if (plugin.getConfig().getBoolean("Enabled.Jacket" + Utils.convertPower(counter))) {
                        p.setMaxHealth(p.getMaxHealth() - counter);
                    }
                }
            }
        }
        if (Utils.hasenchant(plugin.getConfig().getString("Translate.Aquatic") + " I", OldItem)) {
            if (p.hasPotionEffect(PotionEffectType.WATER_BREATHING)) {
                p.removePotionEffect(PotionEffectType.WATER_BREATHING);
            }
        }
        java.io.File customenchants = new java.io.File("plugins//CustomEnchants//CustomEnchants.yml");
        YamlConfiguration enchants = YamlConfiguration.loadConfiguration(customenchants);
        if (enchants.getConfigurationSection("Enchantments") != null) {
            for (String Enchant : enchants.getConfigurationSection("Enchantments").getKeys(false)) {
                for (int counter = 0; counter <= enchants.getInt("Enchantments." + Enchant + ".MaxPower"); counter++) {

                    if (Utils.hasenchant(Enchant + " " + Utils.convertPower(counter), OldItem)) {
                        ArrayList<String> Potions = (ArrayList<String>) enchants
                                .getList("Enchantments." + Enchant + ".Options.PotionEffects");
                        for (String Potion : Potions) {
                            if (PotionEffectType.getByName(Potion) != null) {
                                if (p.hasPotionEffect(PotionEffectType.getByName(Potion))) {
                                    p.removePotionEffect(PotionEffectType.getByName(Potion));
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}
