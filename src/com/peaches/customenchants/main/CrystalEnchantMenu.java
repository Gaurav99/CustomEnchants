package com.peaches.customenchants.main;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CrystalEnchantMenu {
    public final Inventory inv;
    private int total;
    private final ItemStack crystal;

    public CrystalEnchantMenu(ItemStack crystal) {
        this.inv = org.bukkit.Bukkit.getServer().createInventory(null, 36, Utils.format("&8CustomEnchants"));
        this.crystal = crystal;
        this.total = 0;
    }

    public void applyEnchants(@NotNull Player player, int slot) {
        if (this.crystal.getAmount() > 1) {
            ItemStack[] aitemstack;
            int j = (aitemstack = player.getInventory().getContents()).length;
            for (int i = 0; i < j; i++) {
                ItemStack itm = aitemstack[i];
                if (Utils.isCrystal(itm)) {
                    itm.setAmount(itm.getAmount() - 1);
                    break;
                }
            }
        } else {
            player.getInventory().removeItem(this.crystal);
        }
        player.closeInventory();
        player.playSound(player.getLocation(), Sound.ANVIL_USE, 1.0F, 1.0F);

        player.updateInventory();
    }

    public void addItem() {
        this.total += 1;
    }

    public void show(@NotNull Player p) {
        p.openInventory(this.inv);
    }
}


