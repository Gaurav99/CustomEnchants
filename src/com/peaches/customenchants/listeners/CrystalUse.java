package com.peaches.customenchants.listeners;

import com.peaches.customenchants.main.CrystalEnchantMenu;
import com.peaches.customenchants.main.Main;
import com.peaches.customenchants.main.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

public class CrystalUse implements org.bukkit.event.Listener {
    private static Main plugin;

    public CrystalUse(Main pl) {
        plugin = pl;
    }

    @EventHandler
    public void onPlayerInteract(@NotNull PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        if (Utils.isCrystal(item)) {
            File customenchants = new File("plugins//CustomEnchants//CustomEnchants.yml");
            YamlConfiguration enchants = YamlConfiguration.loadConfiguration(customenchants);
            File msg = new File("plugins//CustomEnchants//Messages.yml");
            YamlConfiguration messages = YamlConfiguration.loadConfiguration(msg);
            org.bukkit.event.block.Action a = e.getAction();
            if ((a == Action.PHYSICAL) || (item == null) || (item.getType() == Material.AIR)) {
                return;
            }
            String itemname = ChatColor.stripColor(item.getItemMeta().getDisplayName().replace(" ", ""));
            if ((!plugin.getConfig().getBoolean("Enabled." + itemname))
                    && (plugin.getConfig().contains("Enabled." + itemname))) {
                e.getPlayer().sendMessage(Utils.prefix()
                        + ChatColor.translateAlternateColorCodes('&', messages.getString("EnchantDisabled").toLowerCase().replace("%enchant%", itemname)));
                return;
            }
            String Enchantname = itemname.replace("I", "").replace("V", "").replace("X", "").replace(" ", "");
            if ((!enchants.getBoolean("Enchantments." + Enchantname + ".Enabled"))
                    && (enchants.contains("Enchantments." + Enchantname + ".Enabled"))) {
                e.getPlayer().sendMessage(Utils.prefix()
                        + ChatColor.translateAlternateColorCodes('&', messages.getString("EnchantDisabled").toLowerCase().replace("%enchant%", itemname)));
                return;
            }
            int total = 0;
            Player p = e.getPlayer();
            CrystalEnchantMenu menu = new CrystalEnchantMenu(item);
            for (int i = 0; i < e.getPlayer().getInventory().getContents().length; i++) {
                ItemStack itm = e.getPlayer().getInventory().getContents()[i];
                String x = ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                        .replace(" Enchant", "").toUpperCase());

                if (x.toLowerCase().contains("bow")) {
                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("bow"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }
                }
                if (x.toLowerCase().contains("global")) {
                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("boots"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }

                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("leggings"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }

                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("chestplate"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }

                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("helmet"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }
                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("spade"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        menu.inv.setItem(i, itm);
                        menu.addItem();
                        total++;
                    }

                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("axe"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }
                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("sword"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }

                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("axe"))
                            && (!itm.getType().name().toLowerCase().contains("pick"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }
                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("bow"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }
                }
                if (x.toLowerCase().contains("armor")) {
                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("boots"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }

                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("leggings"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }

                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("chestplate"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }

                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("helmet"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }

                }
                if (x.toLowerCase().contains("tools")) {
                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("spade"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        menu.inv.setItem(i, itm);
                        menu.addItem();
                        total++;
                    }

                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("axe"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }

                }
                if (x.toLowerCase().contains("weapons")) {
                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("sword"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }

                    if ((itm != null) && (!itm.getType().equals(Material.AIR))
                            && (itm.getType().name().toLowerCase().contains("axe"))
                            && (!itm.getType().name().toLowerCase().contains("pick"))
                            && (!itm.getType().name().toLowerCase()
                            .endsWith("pick" + ChatColor
                                    .stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                            .toLowerCase().replace(" enchant", ""))))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }

                }
                if ((itm != null) && (!itm.getType().equals(Material.AIR))
                        && (itm.getType().name().contains(x))) {
                    if (!itm.getType().name().toLowerCase().endsWith(
                            "pick" + ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(0)
                                    .toLowerCase().replace(" enchant", "")))) {
                        if (i < 36) {
                            menu.inv.setItem(i, itm);
                            menu.addItem();
                            total++;
                        }
                    }
                }
            }
            if (total == 0) {
                p.sendMessage(
                        Utils.prefix() + ChatColor.translateAlternateColorCodes('&', messages.getString("NoItems")));
                return;
            }
            menu.show(p);
        }
    }

    @EventHandler
    public void onPickItem(@NotNull InventoryClickEvent e) {
        if (e.getInventory().getTitle() != null) {
            if (e.getInventory().getTitle().contains(Utils.format("&8CustomEnchants"))) {
                e.setCancelled(true);
                if (e.getCurrentItem() == null || e.getCurrentItem().equals(Utils.getair())) {
                    return;
                }
                if (e.getWhoClicked().getItemInHand() == null) {
                    e.getWhoClicked().closeInventory();
                    return;
                }
                if (!Utils.isCrystal(e.getWhoClicked().getItemInHand())) {
                    e.getWhoClicked().closeInventory();
                    return;
                }
                Player p = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();
                if (!e.getClickedInventory().getTitle().contains(Utils.format("&8CustomEnchants"))) {
                    return;
                }
                String c = ChatColor.stripColor(p.getItemInHand().getItemMeta().getDisplayName());
                if (p.getInventory().getItem(e.getSlot()) != null) {
                    if ((p.getInventory().getItem(e.getSlot()).hasItemMeta())
                            && (p.getInventory().getItem(e.getSlot()).getItemMeta().hasLore())) {
                        ArrayList<String> lore = (ArrayList) p.getInventory().getItem(e.getSlot()).getItemMeta().getLore();
                        int size = lore.size();
                        String[] name = p.getItemInHand().getItemMeta().getDisplayName().split(" ");
                        for (String Lore1 : lore) {
                            if (Lore1.contains(ChatColor.GRAY + ChatColor.stripColor(name[0]))) {
                                size--;
                            }
                        }
                        if (size >= plugin.getConfig().getInt("Options.MaxEnchants")) {
                            e.setCancelled(true);
                            File msg = new File("plugins//CustomEnchants//Messages.yml");
                            YamlConfiguration messages = YamlConfiguration.loadConfiguration(msg);
                            p.sendMessage(Utils.prefix()
                                    + ChatColor.translateAlternateColorCodes('&', messages.getString("ToMuchEnchants")));
                            return;
                        }
                    }
                    p.getInventory().setItem(e.getSlot(), Utils.addGlow(Utils.addLore(item, c)));
                    p.closeInventory();
                    if (p.getItemInHand().getAmount() > 0) {
                        if (p.getItemInHand().getAmount() == 1) {
                            p.setItemInHand(Utils.getair());
                        } else {
                            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
                        }
                    }
                    p.updateInventory();
                }
            }

        }
    }
}
