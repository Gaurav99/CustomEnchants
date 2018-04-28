package com.peaches.customenchants.events;

import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class PlayerDamageEvent implements org.bukkit.event.Listener {
    private static Main plugin;

    public PlayerDamageEvent(Main pl) {
        plugin = pl;
    }

    @SuppressWarnings({"unchecked"})
    @EventHandler
    public void OnPlayerDamage(@NotNull EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getDamage() == 0.0D) {
            return;
        }
        if ((e.getDamager() instanceof Arrow)) {
            Arrow arrow = (Arrow) e.getDamager();
            if (plugin.Flare.contains(arrow)) {
                plugin.Flare.remove(arrow);
                arrow.getWorld().createExplosion(arrow.getLocation().getX(), arrow.getLocation().getY(),
                        arrow.getLocation().getZ(), 5.0F, false, false);
            }
        }
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getDamager();
        Player player = (Player) e.getEntity();
        if (com.peaches.customenchants.Support.Support.isFriendly(p, player)) {
            return;
        }
        if (!com.peaches.customenchants.Support.Support.allowsPVP(p.getLocation())) {
            return;
        }
        Damageable pl = (Damageable) e.getDamager();




        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Headshot"); counter++) {
            if (Utils.hasenchant(
                    plugin.getConfig().getString("Translate.Headshot") + " " + Utils.convertPower(counter),
                    player.getInventory().getChestplate()) && plugin.getConfig().getBoolean("Enabled.Headshot" + Utils.convertPower(counter))) {
                if (e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
                    Projectile projectile = (Projectile) e.getDamager();
                    Player shooter = (Player) projectile.getShooter();
                    if ((shooter != null)) {
                        Entity damaged = e.getEntity();
                        double projectileY = projectile.getLocation().getY();
                        double damagedY = damaged.getLocation().getY();
                        if ((damaged instanceof Player) && (projectileY - damagedY > 1.35D)) {
                            e.setDamage(e.getDamage()*(counter+1));
                        }
                    }
                }
            }
        }


        if ((player.getInventory().getChestplate() != null)
                && (player.getInventory().getChestplate().getItemMeta() != null)
                && (player.getInventory().getChestplate().getItemMeta().getLore() != null)) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Ignition") + " I",
                    player.getInventory().getChestplate()) && plugin.getConfig().getBoolean("Enabled.IgnitionI")) {
                Random object = new Random();
                for (int counter = 1; counter <= 1; counter++) {
                    int i = 1 + object.nextInt(100);
                    if (i <= Utils.getIgnitionI()) {
                        p.setFireTicks(100);
                    }
                }
            }

            for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Enlightened"); counter++) {
                if (Utils.hasenchant(
                        plugin.getConfig().getString("Translate.Enlightened") + " " + Utils.convertPower(counter),
                        player.getInventory().getChestplate()) && plugin.getConfig().getBoolean("Enabled.Enlightened" + Utils.convertPower(counter))) {
                    Random object = new Random();

                    int i = 1 + object.nextInt(100);
                    if (!plugin.getConfig().contains("Chances.Enlightened" + Utils.convertPower(counter))) {
                        plugin.getConfig().set("Chances.Enlightened" + Utils.convertPower(counter), 10);
                    }
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    if (i < plugin.getConfig().getInt("Chances.Enlightened" + Utils.convertPower(counter))) {
                        if ((player.getHealth() + (counter * 2)) <= player.getMaxHealth()) {
                            player.setHealth(player.getHealth() + (counter));
                        }
                    }
                }
            }

            for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Evade"); counter++) {
                if (Utils.hasenchant(
                        plugin.getConfig().getString("Translate.Evade") + " " + Utils.convertPower(counter),
                        player.getInventory().getChestplate()) && plugin.getConfig().getBoolean("Enabled.Evade" + Utils.convertPower(counter))) {
                    Random object = new Random();

                    int i = 1 + object.nextInt(100);
                    if (!plugin.getConfig().contains("Chances.Evade" + Utils.convertPower(counter))) {
                        plugin.getConfig().set("Chances.Evade" + Utils.convertPower(counter), 10);
                    }
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    if (i < plugin.getConfig().getInt("Chances.Evade" + Utils.convertPower(counter))) {
                        e.setDamage(0.00);
                    }
                }
            }
        }

        if (p.getItemInHand() == null) {
            return;
        }
        if (p.getItemInHand().getItemMeta() == null) {
            return;
        }
        if (p.getItemInHand().getItemMeta().getLore() == null) {
            return;
        }

        if (Utils.hasenchant(plugin.getConfig().getString("Translate.ThunderusBlow") + " I",
                p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.ThunderusBlowI")) {
            Random object = new Random();
            for (int counter = 1; counter <= 1; counter++) {
                int i = 1 + object.nextInt(100);
                player.getWorld().strikeLightning(player.getLocation());
            }
        }


        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Warden"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Warden") + " " + Utils.convertPower(counter),
                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Warden" + Utils.convertPower(counter))) {
                Random object = new Random();
                int i = 1 + object.nextInt(100);
                if (!plugin.getConfig().contains("Chances.Warden" + Utils.convertPower(counter))) {
                    plugin.getConfig().set("Chances.Warden" + Utils.convertPower(counter), 5);
                }
                plugin.saveConfig();
                plugin.reloadConfig();
                if (i < plugin.getConfig().getInt("Chances.Warden" + Utils.convertPower(counter))) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20*10, -1 + counter, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20*10, -1 + counter, false));
                }
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Frostbite"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Frostbite") + " " + Utils.convertPower(counter),
                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Frostbite" + Utils.convertPower(counter))) {
                Random object = new Random();
                int i = 1 + object.nextInt(100);
                if (!plugin.getConfig().contains("Chances.Frostbite" + Utils.convertPower(counter))) {
                    plugin.getConfig().set("Chances.Frostbite" + Utils.convertPower(counter), 5);
                }
                plugin.saveConfig();
                plugin.reloadConfig();
                if (i < plugin.getConfig().getInt("Chances.Frostbite" + Utils.convertPower(counter))) {
                    plugin.FrostBite(player, 60 + (40 * counter));
                }
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Fury"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Fury") + " " + Utils.convertPower(counter),
                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Fury" + Utils.convertPower(counter))) {
                if (plugin.FuryData.containsKey(p.getName())) {
                    plugin.FuryData.put(p.getName(), plugin.FuryData.get(p.getName()) + 1);
                    plugin.FuryTime.put(p.getName(), 2);
                } else {
                    plugin.FuryData.put(p.getName(), 1);
                    plugin.FuryTime.put(p.getName(), 2);
                }
                e.setDamage(e.getDamage() * (1 + plugin.FuryData.get(p.getName())));
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Penetrate"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Penetrate") + " " + Utils.convertPower(counter), p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Penetrate" + Utils.convertPower(counter))) {
                plugin.addpenetrate(player.getName(), counter);
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Paralyze"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Paralyze") + " " + Utils.convertPower(counter),
                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Paralyze" + Utils.convertPower(counter))) {
                Random object = new Random();

                int i = 1 + object.nextInt(100);
                if (!plugin.getConfig().contains("Chances.Paralyze" + Utils.convertPower(counter))) {
                    plugin.getConfig().set("Chances.Paralyze" + Utils.convertPower(counter), 10);
                }
                plugin.saveConfig();
                plugin.reloadConfig();
                if (i < plugin.getConfig().getInt("Chances.Paralyze" + Utils.convertPower(counter))) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (4 + counter * 2) * 20, 10, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (4 + counter * 2) * 20, -10, false));
                }
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Blindness"); counter++) {
            if (Utils.hasenchant(
                    plugin.getConfig().getString("Translate.Blindness") + " " + Utils.convertPower(counter),
                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Blindness" + Utils.convertPower(counter))) {
                Random object = new Random();

                int i = 1 + object.nextInt(100);
                if (!plugin.getConfig().contains("Chances.Blindness" + Utils.convertPower(counter))) {
                    plugin.getConfig().set("Chances.Blindness" + Utils.convertPower(counter), 10);
                }
                plugin.saveConfig();
                plugin.reloadConfig();
                if (i < plugin.getConfig().getInt("Chances.Blindness" + Utils.convertPower(counter))) {
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.BLINDNESS, (4 + counter * 2) * 20, 0, false));
                }
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Poison"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Poison") + " " + Utils.convertPower(counter),
                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Poison" + Utils.convertPower(counter))) {
                Random object = new Random();

                int i = 1 + object.nextInt(100);
                if (!plugin.getConfig().contains("Chances.Poison" + Utils.convertPower(counter))) {
                    plugin.getConfig().set("Chances.Poison" + Utils.convertPower(counter), 10);
                }
                plugin.saveConfig();
                plugin.reloadConfig();
                if (i < plugin.getConfig().getInt("Chances.Poison" + Utils.convertPower(counter))) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, counter - 1, false));
                }
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Wither"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Wither") + " " + Utils.convertPower(counter),
                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Wither" + Utils.convertPower(counter))) {
                Random object = new Random();

                int i = 1 + object.nextInt(100);
                if (!plugin.getConfig().contains("Chances.Wither" + Utils.convertPower(counter))) {
                    plugin.getConfig().set("Chances.Wither" + Utils.convertPower(counter), 10);
                }
                plugin.saveConfig();
                plugin.reloadConfig();
                if (i < plugin.getConfig().getInt("Chances.Wither" + Utils.convertPower(counter))) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, counter - 1, false));
                }
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Nausea"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Nausea") + " " + Utils.convertPower(counter),
                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Nausea" + Utils.convertPower(counter))) {
                Random object = new Random();

                int i = 1 + object.nextInt(100);
                if (!plugin.getConfig().contains("Chances.Nausea" + Utils.convertPower(counter))) {
                    plugin.getConfig().set("Chances.Nausea" + Utils.convertPower(counter), 10);
                }
                plugin.saveConfig();
                plugin.reloadConfig();
                if (i < plugin.getConfig().getInt("Chances.Nausea" + Utils.convertPower(counter))) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (8 + counter * 2) * 20, 1, false));
                }
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Slowness"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Slowness") + " " + Utils.convertPower(counter),
                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Slowness" + Utils.convertPower(counter))) {
                Random object = new Random();

                int i = 1 + object.nextInt(100);
                if (!plugin.getConfig().contains("Chances.Slowness" + Utils.convertPower(counter))) {
                    plugin.getConfig().set("Chances.Slowness" + Utils.convertPower(counter), 10);
                }
                plugin.saveConfig();
                plugin.reloadConfig();
                if (i < plugin.getConfig().getInt("Chances.Slowness" + Utils.convertPower(counter))) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, counter - 1, false));
                }
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Lifesteal"); counter++) {
            if (Utils.hasenchant(
                    plugin.getConfig().getString("Translate.Lifesteal") + " " + Utils.convertPower(counter),
                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Lifesteal" + Utils.convertPower(counter))) {
                Random object = new Random();

                int i = 1 + object.nextInt(100);
                if (!plugin.getConfig().contains("Chances.Lifesteal" + Utils.convertPower(counter))) {
                    plugin.getConfig().set("Chances.Lifesteal" + Utils.convertPower(counter), 10);
                }
                plugin.saveConfig();
                plugin.reloadConfig();
                if (i < plugin.getConfig().getInt("Chances.Lifesteal" + Utils.convertPower(counter))) {
                    if (pl.getHealth() < 20 - counter * 2) {
                        pl.setHealth(pl.getHealth() + counter * 2);
                    } else {
                        pl.setHealth(20.0D);
                    }
                }
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Cursed"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Cursed") + " " + Utils.convertPower(counter),
                    p.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Cursed" + Utils.convertPower(counter))) {
                Random object = new Random();

                int i = 1 + object.nextInt(100);
                if (!plugin.getConfig().contains("Chances.Cursed" + Utils.convertPower(counter))) {
                    plugin.getConfig().set("Chances.Cursed" + Utils.convertPower(counter), 10);
                }
                plugin.saveConfig();
                plugin.reloadConfig();
                if (i < plugin.getConfig().getInt("Chances.Cursed" + Utils.convertPower(counter))) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1 + counter * 2 * 20, 0, false));
                }
            }
        }
        java.io.File customenchants = new java.io.File("plugins//CustomEnchants//CustomEnchants.yml");
        YamlConfiguration enchants = YamlConfiguration.loadConfiguration(customenchants);
        for (String Enchant : enchants.getConfigurationSection("Enchantments").getKeys(false)) {
            for (int counter = 0; counter <= enchants.getInt("Enchantments." + Enchant + ".MaxPower"); counter++) {

                if (Utils.hasenchant(Enchant + " " + Utils.convertPower(counter), p.getItemInHand())) {
                    ArrayList<String> Potions = (ArrayList<String>) enchants
                            .getList("Enchantments." + Enchant + ".Options.PotionEffects");
                    Random r = new Random();
                    for (String Potion : Potions) {
                        if (1 + r.nextInt(100) < enchants.getInt("Enchantments." + Enchant + ".Chance")) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(Potion),
                                    enchants.getInt("Enchantments." + Enchant + ".Options.PotionTime") * 20,
                                    -1 + counter * enchants.getInt("Enchantments." + Enchant + ".Options.PowerIncrease")
                                            + (enchants.getInt("Enchantments." + Enchant + ".StartingPower") - 1),
                                    false));
                        }
                    }
                    if (1 + r.nextInt(100) <= enchants.getInt("Enchantments." + Enchant + ".Chance")) {
                        e.setDamage(e.getDamage()
                                * enchants.getDouble("Enchantments." + Enchant + ".Options.DamageModifier"));
                    }
                }
            }
        }
    }
}






