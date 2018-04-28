package com.peaches.customenchants.events;

import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class PlayerDeathEvent implements org.bukkit.event.Listener {
    private static Main plugin;

    public PlayerDeathEvent(Main pl) {
        plugin = pl;
    }

    @EventHandler
    public void PlayerDeath(@NotNull EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            if ((e.getEntity().getKiller() != null)) {
                Player damager = e.getEntity().getKiller();
                if (damager.getItemInHand() == null) {
                    return;
                }
                if (damager.getItemInHand().getItemMeta() == null) {
                    return;
                }
                if (damager.getItemInHand().getItemMeta().getLore() == null) {
                    return;
                }
                for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Inquisitive"); counter++) {
                    if (Utils.hasenchant(
                            plugin.getConfig().getString("Translate.Inquisitive") + " " + Utils.convertPower(counter),
                            damager.getItemInHand()) && plugin.getConfig().getBoolean("Enabled.Inquisitive" + Utils.convertPower(counter))) {
                        e.setDroppedExp(e.getDroppedExp() * counter + 1);
                        return;
                    }
                }
            }
        } else if ((e.getEntity().getKiller() != null)) {

            Player damager = e.getEntity().getKiller();
            Player player = (Player) e.getEntity();

            if (damager.getItemInHand() == null) {
                return;
            }
            if (damager.getItemInHand().getItemMeta() == null) {
                return;
            }
            if (damager.getItemInHand().getItemMeta().getLore() == null) {
                return;
            }
            ItemStack item = damager.getItemInHand();
            for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Reborn"); counter++) {
                if (Utils.hasenchant(
                        plugin.getConfig().getString("Translate.Reborn") + " " + Utils.convertPower(counter), item) && plugin.getConfig().getBoolean("Enabled.Reborn" + Utils.convertPower(counter))) {
                    Random object = new Random();

                    int i = 1 + object.nextInt(100);
                    if (!plugin.getConfig().contains("Chances.Reborn" + Utils.convertPower(counter))) {
                        plugin.getConfig().set("Chances.Reborn" + Utils.convertPower(counter), 10);
                    }
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    if (i < plugin.getConfig().getInt("Chances.Reborn" + Utils.convertPower(counter))) {
                        damager.addPotionEffect(
                                new PotionEffect(PotionEffectType.REGENERATION, 200, counter - 1, false));
                        damager.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, counter - 1, false));
                        damager.addPotionEffect(
                                new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, counter - 1, false));
                    }
                }
            }
            if (Utils.hasenchant(plugin.getConfig().getString("Translate.Decapitate") + " I", item) && plugin.getConfig().getBoolean("Enabled.DecapitateI")) {
                ItemStack head = Utils.makeItem("397:3", 1);
                SkullMeta m = (SkullMeta) head.getItemMeta();
                m.setOwner(player.getName());
                head.setItemMeta(m);
                e.getDrops().add(head);
            }
        }
    }
}
