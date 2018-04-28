package com.peaches.customenchants.listeners;

import com.peaches.customenchants.Support.Vault;
import com.peaches.customenchants.main.ConfigManager;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class TinkererListner implements Listener {
    private static Main plugin;

    public TinkererListner(Main pl) {
        plugin = pl;
    }

    @NotNull
    private HashMap<Integer, Integer> getSlot() {
        HashMap<Integer, Integer> slots = new HashMap<>();
        slots.put(1, 5);
        slots.put(2, 6);
        slots.put(3, 7);
        slots.put(9, 14);
        slots.put(10, 15);
        slots.put(11, 16);
        slots.put(12, 17);
        slots.put(18, 23);
        slots.put(19, 24);
        slots.put(20, 25);
        slots.put(21, 26);
        slots.put(27, 32);
        slots.put(28, 33);
        slots.put(29, 34);
        slots.put(30, 35);
        slots.put(36, 41);
        slots.put(37, 42);
        slots.put(38, 43);
        slots.put(39, 44);
        slots.put(45, 50);
        slots.put(46, 51);
        slots.put(47, 52);
        slots.put(48, 53);
        return slots;
    }

    private int nextslot(Inventory inv) {
        return inv.firstEmpty();
    }

    @EventHandler
    public void onclose(@NotNull InventoryCloseEvent e) {
        if (e.getInventory().getTitle() != null) {
            if (e.getInventory().getTitle().equals(Utils.Tinkerer().getTitle())) {
                if (e.getInventory().getItem(0) != null) {
                    if (e.getInventory().getItem(0).equals(Utils.Tinkerer().getItem(0))) {
                        Player p = (Player) e.getPlayer();
                        DeclineTrade(p, e.getInventory());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClick(@NotNull InventoryClickEvent e) {
        if (e.getInventory().getTitle() != null) {
            if (e.getInventory().getTitle().equals(Utils.Tinkerer().getTitle())) {
                if (e.getCurrentItem() == null) {
                    return;
                }
                Player p = (Player) e.getWhoClicked();
                if (e.getInventory().getItem(0).equals(Utils.Tinkerer().getItem(0))) {
                    e.setCancelled(true);
                    if (e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR)) {
                        if (e.getCurrentItem().hasItemMeta()) {
                            if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', ConfigManager.getInstance().getTinker().getString("Options.AcceptTrade")))) {
                                    for (int slot : getSlot().keySet()) {
                                        if (e.getInventory().getItem(getSlot().get(slot)) != null && !e.getInventory().getItem(getSlot().get(slot)).getType().equals(Material.AIR)) {
                                            if (e.getInventory().getItem(getSlot().get(slot)).getItemMeta().getDisplayName().contains("$")) {
                                                String name = ChatColor.stripColor(e.getInventory().getItem(getSlot().get(slot)).getItemMeta().getDisplayName().replace("$", ""));
                                                Vault.addbal(p, Double.parseDouble(name));
                                            } else {
                                                String name = ChatColor.stripColor(e.getInventory().getItem(getSlot().get(slot)).getItemMeta().getDisplayName().replace(" Xp", ""));
                                                Utils.RemoveXP(p, 0 - Integer.parseInt(name));
                                            }
                                        }
                                    }
                                    e.getInventory().clear();
                                    p.closeInventory();
                                    return;
                                }
                                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', ConfigManager.getInstance().getTinker().getString("Options.DeclineTrade")))) {
                                    p.closeInventory();
                                    return;
                                }
                            }
                            if (e.getCurrentItem().getItemMeta().hasLore() || e.getCurrentItem().getItemMeta().hasEnchants()) { // Adding/Removing an item
                                int slot = nextslot(e.getInventory());

                                if (Utils.isCrystal(e.getCurrentItem())) {
                                    if (!e.getClickedInventory().equals(e.getInventory())) {
                                        e.getInventory().setItem(slot, e.getCurrentItem());
                                        e.getInventory().setItem(getSlot().get(slot), getItem(e.getCurrentItem()));
                                        e.setCurrentItem(new ItemStack(Material.AIR));
                                    } else {
                                        e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
                                        e.getInventory().setItem(getSlot().get(e.getSlot()), new ItemStack(Material.AIR));
                                        e.setCurrentItem(new ItemStack(Material.AIR));
                                    }
                                    return;
                                }
                                if (!e.getClickedInventory().equals(e.getInventory())) {
                                    e.getInventory().setItem(slot, e.getCurrentItem());
                                    e.getInventory().setItem(getSlot().get(slot), getItem(e.getCurrentItem()));
                                    e.setCurrentItem(new ItemStack(Material.AIR));
                                } else {
                                    e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
                                    e.getInventory().setItem(getSlot().get(e.getSlot()), new ItemStack(Material.AIR));
                                    e.setCurrentItem(new ItemStack(Material.AIR));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @NotNull
    private ItemStack getItem(@NotNull ItemStack item) {
        Material mat = Material.getMaterial(ConfigManager.getInstance().getTinker().getString("Options.Item"));
        if (ConfigManager.getInstance().getTinker().getString("Options.Payment").equals("Vault")) {
            return Utils.makeItem(mat, 1, 0, ChatColor.RED + "" + ChatColor.BOLD + "$" + getTotalXP(item));
        }
        return Utils.makeItem(mat, 1, 0, ChatColor.RED + "" + ChatColor.BOLD + getTotalXP(item) + " Xp");
    }

    private int getTotalXP(ItemStack item) {
        int total = 0;
        for (int i = 1; i <= item.getAmount(); i++) {
            if (item.hasItemMeta()) {
                if (item.getItemMeta().hasEnchants()) {
                    for (Enchantment enchant : item.getItemMeta().getEnchants().keySet()) {
                        if (ConfigManager.getInstance().getTinker().contains("Tinker.Vanilla-Enchantments." + enchant.getName() + "." + item.getItemMeta().getEnchants().get(enchant))) {
                            total += ConfigManager.getInstance().getTinker().getInt("Tinker.Vanilla-Enchantments." + enchant.getName() + "." + item.getItemMeta().getEnchants().get(enchant));
                        }
                    }
                }
            }
            for (String Enchant : Utils.type.keySet()) {
                if (plugin.getConfig().contains("MaxPower." + Enchant)) {
                    for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower." + Enchant); counter++) {
                        if (Utils.hasenchant(Enchant + " " + Utils.convertPower(counter), item)) {
                            if (ConfigManager.getInstance().getTinker().contains("Tinker.Custom-Enchantments." + Enchant + "." + counter)) {
                                total += ConfigManager.getInstance().getTinker().getInt("Tinker.Custom-Enchantments." + Enchant + "." + counter);
                            }
                        }
                        if (Utils.hasenchant(plugin.getConfig().getString("Translate." + Enchant) + " " + Utils.convertPower(counter), item)) {
                            if (ConfigManager.getInstance().getTinker().contains("Tinker.Custom-Enchantments." + Enchant + "." + counter)) {
                                total += ConfigManager.getInstance().getTinker().getInt("Tinker.Custom-Enchantments." + Enchant + "." + counter);
                            }
                        }

                        if (Utils.isCrystal(item)) {
                            if (ChatColor.stripColor(item.getItemMeta().getDisplayName().toLowerCase()).equals((Enchant + " " + Utils.convertPower(counter)).toLowerCase())) {
                                if (ConfigManager.getInstance().getTinker().contains("Tinker.Custom-Enchantments." + Enchant + "." + counter)) {
                                    total += ConfigManager.getInstance().getTinker().getInt("Tinker.Custom-Enchantments." + Enchant + "." + counter);
                                }
                            }
                            if (ChatColor.stripColor(item.getItemMeta().getDisplayName().toLowerCase()).equals((plugin.getConfig().getString("Translate." + Enchant) + " " + Utils.convertPower(counter)).toLowerCase())) {
                                if (ConfigManager.getInstance().getTinker().contains("Tinker.Custom-Enchantments." + Enchant + "." + counter)) {
                                    total += ConfigManager.getInstance().getTinker().getInt("Tinker.Custom-Enchantments." + Enchant + "." + counter);
                                }
                            }
                        }
                    }
                } else {
                    if (Utils.hasenchant(Enchant + "I", item)) {

                        if (ConfigManager.getInstance().getTinker().contains("Tinker.Custom-Enchantments." + Enchant + ".1")) {
                            total += ConfigManager.getInstance().getTinker().getInt("Tinker.Custom-Enchantments." + Enchant + ".1");
                        }
                    }
                    if (Utils.isCrystal(item)) {
                        if (ChatColor.stripColor(item.getItemMeta().getDisplayName().toLowerCase()).equals((Enchant + " I").toLowerCase())) {
                            if (ConfigManager.getInstance().getTinker().contains("Tinker.Custom-Enchantments." + Enchant + ".1")) {
                                total += ConfigManager.getInstance().getTinker().getInt("Tinker.Custom-Enchantments." + Enchant + ".1");
                            }
                        }
                    }
                }
            }
        }
        return total;
    }

    private void DeclineTrade(Player player, Inventory inv) {
        for (int slot : getSlot().keySet()) {
            if (inv.getItem(slot) != null) {
                if (inv.getItem(slot).getType() != Material.AIR) {
                    if (player.isDead()) {
                        player.getWorld().dropItem(player.getLocation(), inv.getItem(slot));
                    } else {
                        if (Utils.hasOpenSlot(player.getInventory())) {
                            player.getInventory().addItem(inv.getItem(slot));
                        } else {
                            player.getWorld().dropItem(player.getLocation(), inv.getItem(slot));
                        }
                    }
                }
            }
        }
        inv.clear();
        player.updateInventory();
    }
}
