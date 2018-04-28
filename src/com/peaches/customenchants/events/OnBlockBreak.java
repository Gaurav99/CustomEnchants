package com.peaches.customenchants.events;

import com.google.common.collect.Lists;
import com.peaches.customenchants.Support.Support;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OnBlockBreak implements Listener {
    private static Main plugin;
    private static final ArrayList<Location> blocklist = new ArrayList<>();

    public OnBlockBreak(Main pl) {
        plugin = pl;
    }

    @NotNull
    private static List<Block> getCube(@NotNull Location loc, Integer radius) {
        List<Block> blocks = Lists.newArrayList();
        for (int x = radius * -1; x <= radius; x++) {
            for (int y = radius * -1; y <= radius; y++) {
                for (int z = radius * -1; z <= radius; z++) {
                    Block b = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                    if (!b.getType().equals(Material.AIR)) {
                        blocks.add(b);
                    }
                }
            }
        }
        return blocks;
    }

    @EventHandler
    public void onBlockBreak(@NotNull BlockBreakEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (blocklist.contains(e.getBlock().getLocation())) {
            blocklist.remove(e.getBlock().getLocation());
            return;
        }
        Player player = e.getPlayer();
        ItemStack item = player.getItemInHand();
        Block block1 = e.getBlock();
        if (plugin.containsblocktask(block1.getLocation())) {
            e.setCancelled(true);
            block1.setType(Material.AIR);
            plugin.removeblocktask(block1.getLocation());
        }
        if (item == null) {
            return;
        }
        if (item.getItemMeta() == null) {
            return;
        }
        if (item.getItemMeta().getLore() == null) {
            return;
        }
        for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower.Infusion"); counter++) {

            if (plugin.getConfig().getBoolean("Enabled.Infusion" + Utils.convertPower(counter))) {
                if ((Utils.hasenchant(
                        plugin.getConfig().getString("Translate.Infusion") + " " + Utils.convertPower(counter), item)) && (!e.getPlayer().isSneaking())) {
                    e.setCancelled(true);
                    Location loc = e.getBlock().getLocation();
                    List<Block> blocks = getCube(loc, counter);
                    if (blocks.isEmpty()) {
                        blocks.add(e.getBlock());
                    }
                    for (Block block : blocks) {
                        if (block != null && block.getType() != Material.AIR && block.getType() != Material.BEDROCK && block.getType() != Material.STATIONARY_LAVA && block.getType() != Material.AIR && block.getType() != null && block.getType() != Material.STATIONARY_WATER && block.getType() != Material.WATER && block.getType() != Material.LAVA && Support.canBreakBlock(player, block) && Support.allowsBreak(block.getLocation(), player)) {
                            if (plugin.getConfig().getBoolean("Options.UseBlockBreakEvent")) {
                                blocklist.add(block.getLocation());
                                Support.AddExemption(player);
                                BlockBreakEvent event = new BlockBreakEvent(block, player);
                                Bukkit.getPluginManager().callEvent(event);
                                if (!event.isCancelled()) {
                                    if (Utils.hasenchant(plugin.getConfig().getString("Translate.Blazingtouch") + " I",
                                            item)) {
                                        if (smeltedItem(block.getType()) != null) {
                                            block.getWorld().dropItemNaturally(block.getLocation(),
                                                    smeltedItem(block.getType()));
                                            block.setType(Material.AIR);
                                            for (int i = 0; i < 10; i += 5) {
                                                loc.getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES,
                                                        5);
                                            }
                                        }
                                    }
                                        block.breakNaturally();
                                }
                            } else {
                                blocklist.add(block.getLocation());
                                if (Utils
                                        .hasenchant(plugin.getConfig().getString("Translate.Blazingtouch") + " I", item)) {
                                    if (smeltedItem(block.getType()) != null) {
                                        block.getWorld().dropItemNaturally(block.getLocation(),
                                                smeltedItem(block.getType()));
                                        block.setType(Material.AIR);
                                        for (int i = 0; i < 10; i += 5) {
                                            loc.getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 5);
                                        }
                                    }
                                }
                                    block.breakNaturally();
                            }
                        }
                    }
                    Support.RemoveExemption(player);
                    return;
                }
            }
        }

        if (plugin.getConfig().getBoolean("Enabled.BlazingtouchI")) {
            if ((Utils.hasenchant(plugin.getConfig().getString("Translate.Blazingtouch") + " I", item))
                    && (!player.getGameMode().equals(GameMode.CREATIVE))) {
                if (e.getBlock() == null) {
                    return;
                }
                if (smeltedItem(e.getBlock().getType()) == null) {
                    return;
                }
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), smeltedItem(e.getBlock().getType()));
                e.getBlock().setType(Material.AIR);
                for (int i = 0; i < 10; i += 5) {
                    Location loc = e.getBlock().getLocation();
                    loc.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 5);
                }
            }
        }
    }

    private ItemStack smeltedItem(Material type) {
        if (type == Material.IRON_ORE) {
            return new ItemStack(Material.IRON_INGOT, 1);
        }
        if (type == Material.GOLD_ORE) {
            return new ItemStack(Material.GOLD_INGOT, 1);
        }
        if (type == Material.SAND) {
            return new ItemStack(Material.GLASS, 1);
        }
        if (type == Material.COBBLESTONE) {
            return new ItemStack(Material.STONE, 1);
        }
        if (type == Material.CLAY) {
            return new ItemStack(Material.HARD_CLAY, 1);
        }
        if (type == Material.NETHERRACK) {
            return new ItemStack(Material.NETHER_BRICK_ITEM, 1);
        }
        return null;
    }
}
