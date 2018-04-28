package com.peaches.customenchants.events;

import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class OnShootBow implements org.bukkit.event.Listener {
    private static Main plugin;

    public OnShootBow(Main pl) {
        plugin = pl;
    }

    @EventHandler
    public void onShootBow(@NotNull EntityShootBowEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getBow() == null) {
            return;
        }
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        final ItemStack bow = e.getBow();
        if (bow == null) {
            return;
        }
        if (bow.getItemMeta() == null) {
            return;
        }
        if (bow.getItemMeta().getLore() == null) {
            return;
        }
        final Player shooter = (Player) e.getEntity();
        Vector velocity = e.getProjectile().getVelocity().clone();
        final int fireTicks = e.getProjectile().getFireTicks();
        final double speed = velocity.length();
        final Vector direction = new Vector(velocity.getX() / speed, velocity.getY() / speed, velocity.getZ() / speed);

//        for (int counter1 = 1; counter1 <= plugin.getConfig().getInt("MaxPower.Target"); counter1++) {
//            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Target") + " " + Utils.convertPower(counter1), bow)) {
//                Random object = new Random();
//                int i1 = 1 + object.nextInt(100);
//                if (i1 < plugin.getConfig().getInt("Chances.Target"+Utils.convertPower(counter1))) {
//                    plugin.Target.put((Projectile) e.getProjectile(), counter1);
//                }
//            }
//        }


        if (Utils.hasenchant(plugin.getConfig().getString("Translate.Flare") + " I", bow) && plugin.getConfig().getBoolean("Enabled.FlareI")) {
            Random object = new Random();
            for (int counter = 1; counter <= 1; counter++) {
                int i1 = 1 + object.nextInt(100);
                if (i1 < Utils.getFlareI()) {
                    for (int counter1 = 1; counter1 <= plugin.getConfig().getInt("MaxPower.Multishot"); counter1++) {
                        if (Utils.hasenchant(plugin.getConfig().getString("Translate.Multishot") + " " + Utils.convertPower(counter1), bow)) {
                            final int number = counter1;
                            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                for (int i = 0; i < number; i++) {
                                    if ((shooter.getGameMode() == org.bukkit.GameMode.CREATIVE) ||
                                            (bow.containsEnchantment(org.bukkit.enchantments.Enchantment.ARROW_INFINITE))) {
                                        Arrow arrow = shooter.launchProjectile(Arrow.class);
                                        arrow.setFireTicks(fireTicks);
                                        arrow.setBounce(false);
                                        arrow.setVelocity(new Vector(direction.getX() + (Math.random() - 0.5D) / 3.5D,
                                                direction.getY() + (Math.random() - 0.5D) / 3.5D,
                                                direction.getZ() + (Math.random() - 0.5D) / 3.5D).normalize().multiply(speed));
                                        arrow.setShooter(shooter);
                                        arrow.setMetadata("Multishot", new FixedMetadataValue(
                                                org.bukkit.Bukkit.getServer().getPluginManager().getPlugin("CustomEnchants"), Boolean.TRUE));
                                    } else {
                                        ItemStack item = new ItemStack(Material.ARROW);
                                        if (shooter.getInventory().containsAtLeast(item, 1)) {
                                            shooter.getInventory().removeItem(item);
                                            Arrow arrow = shooter.launchProjectile(Arrow.class);
                                            arrow.setFireTicks(fireTicks);
                                            arrow.setBounce(false);
                                            arrow.setVelocity(new Vector(direction.getX() + (Math.random() - 0.5D) / 3.5D,
                                                    direction.getY() + (Math.random() - 0.5D) / 3.5D,
                                                    direction.getZ() + (Math.random() - 0.5D) / 3.5D).normalize()
                                                    .multiply(speed));
                                            arrow.setShooter(shooter);
                                        }
                                    }
                                }
                            });
                        }
                    }
                    Projectile arrow = (Projectile) e.getProjectile();
                    plugin.Flare.add(arrow);
                    return;
                }
            }
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Multishot"); counter++) {
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Multishot") + " " + Utils.convertPower(counter), bow) && plugin.getConfig().getBoolean("Enabled.Multishot" + Utils.convertPower(counter))) {
                final int number = counter;
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    for (int i = 0; i < number; i++) {
                        if ((shooter.getGameMode() == org.bukkit.GameMode.CREATIVE) ||
                                (bow.containsEnchantment(org.bukkit.enchantments.Enchantment.ARROW_INFINITE))) {
                            Arrow arrow = shooter.launchProjectile(Arrow.class);
                            arrow.setFireTicks(fireTicks);
                            arrow.setBounce(false);
                            arrow.setVelocity(new Vector(direction.getX() + (Math.random() - 0.5D) / 3.5D,
                                    direction.getY() + (Math.random() - 0.5D) / 3.5D,
                                    direction.getZ() + (Math.random() - 0.5D) / 3.5D).normalize().multiply(speed));
                            arrow.setShooter(shooter);
                            arrow.setMetadata("Multishot", new FixedMetadataValue(
                                    org.bukkit.Bukkit.getServer().getPluginManager().getPlugin("CustomEnchants"), Boolean.TRUE));
                        } else {
                            ItemStack item = new ItemStack(Material.ARROW);
                            if (shooter.getInventory().containsAtLeast(item, 1)) {
                                shooter.getInventory().removeItem(item);
                                Arrow arrow = shooter.launchProjectile(Arrow.class);
                                arrow.setFireTicks(fireTicks);
                                arrow.setBounce(false);
                                arrow.setVelocity(new Vector(direction.getX() + (Math.random() - 0.5D) / 3.5D,
                                        direction.getY() + (Math.random() - 0.5D) / 3.5D,
                                        direction.getZ() + (Math.random() - 0.5D) / 3.5D).normalize()
                                        .multiply(speed));
                                arrow.setShooter(shooter);
                            }
                        }
                    }
                });
            }
        }
    }

    @EventHandler
    public void onpickup(@NotNull PlayerPickupItemEvent e) {
        org.bukkit.entity.Item item = e.getItem();
        if (item.hasMetadata("Multishot")) {
            e.setCancelled(true);
        }
    }
}


