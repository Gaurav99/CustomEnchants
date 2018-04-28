package com.peaches.customenchants.events;

import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R1.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;
import java.util.Set;

public class ActionEvent implements Listener {
    private static Main plugin;
    private final File msg = new File("plugins//CustomEnchants//Messages.yml");
    private final YamlConfiguration messages = YamlConfiguration.loadConfiguration(this.msg);

    public ActionEvent(Main pl) {
        plugin = pl;
    }


    @EventHandler
    public void onclick(PlayerInteractEvent e) {
        if (e.getPlayer().isSneaking()) {
            if (e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.LEFT_CLICK_AIR)) {
                for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Enderforged"); counter++) {
                    if (Utils.hasenchant("Enderforged " + Utils.convertPower(counter), e.getPlayer().getInventory().getChestplate())) {
                        if (e.getPlayer().getLevel() >= plugin.getConfig().getInt("Options.EnderForgedXP")) {
//                            if (!plugin.Abilities.containsKey(e.getPlayer().getName())) {
                            final Location cloc = e.getPlayer().getLocation();
                            Location loc = e.getPlayer().getLocation().getWorld().getHighestBlockAt(e.getPlayer().getTargetBlock((Set<Material>) null, 50 * counter).getLocation()).getLocation();
                            loc.setYaw(cloc.getYaw());
                            loc.setPitch(cloc.getPitch());
                            loc.setY(loc.getY() + 1);
                            Utils.sendParticle(e.getPlayer().getLocation(), EnumParticle.PORTAL);
                            e.getPlayer().getLocation().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 10.0F, 1F);
                            e.getPlayer().teleport(loc);
                            Utils.sendParticle(e.getPlayer().getLocation(), EnumParticle.PORTAL);
                            e.getPlayer().getLocation().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 10.0F, 1F);
                            e.getPlayer().setLevel(e.getPlayer().getLevel() - plugin.getConfig().getInt("Options.EnderForgedXP"));
//                                plugin.Abilities.put(e.getPlayer().getName(), 60);
//                            } else {
//                                File msg = new File("plugins//CustomEnchants//Messages.yml");
//                                YamlConfiguration messages = YamlConfiguration.loadConfiguration(msg);
//                                e.getPlayer().sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&', messages.getString("WaitMessage").replace("%time%", plugin.Abilities.get(e.getPlayer().getName()).toString())));
//                            }
                        } else {
                            e.getPlayer().sendMessage(Utils.prefix() +
                                    ChatColor.translateAlternateColorCodes('&', this.messages.getString("NeedMoreXp")));
                        }
                    }
                }
                for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Stealth"); counter++) {
                    if (Utils.hasenchant("Stealth " + Utils.convertPower(counter), e.getPlayer().getInventory().getChestplate())) {
                        if (e.getPlayer().getLevel() >= plugin.getConfig().getInt("Options.StealthXP")) {
//                            if (!plugin.Abilities.containsKey(e.getPlayer().getName())) {
                            Utils.sendParticle(e.getPlayer().getLocation(), EnumParticle.SMOKE_LARGE);
                            e.getPlayer().getLocation().getWorld().playSound(e.getPlayer().getLocation(), Sound.FIRE_IGNITE, 10.0F, 1F);
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.hidePlayer(e.getPlayer());
                            }
                            plugin.Stealth.put(e.getPlayer().getName(), counter * 2);
                            e.getPlayer().setLevel(e.getPlayer().getLevel() - plugin.getConfig().getInt("Options.StealthXP"));
//                                plugin.Abilities.put(e.getPlayer().getName(), 60);
//                            } else {
//                                File msg = new File("plugins//CustomEnchants//Messages.yml");
//                                YamlConfiguration messages = YamlConfiguration.loadConfiguration(msg);
//                                e.getPlayer().sendMessage(Utils.prefix() + ChatColor.translateAlternateColorCodes('&', messages.getString("WaitMessage").replace("%time%", plugin.Abilities.get(e.getPlayer().getName()).toString())));
//                            }
                        } else {
                            e.getPlayer().sendMessage(Utils.prefix() +
                                    ChatColor.translateAlternateColorCodes('&', this.messages.getString("NeedMoreXp")));
                        }
                    }
                }
            }
        }
    }
}
