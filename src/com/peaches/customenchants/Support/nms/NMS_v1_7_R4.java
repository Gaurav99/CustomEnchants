package com.peaches.customenchants.Support.nms;

import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.jetbrains.annotations.NotNull;


public class NMS_v1_7_R4 {
    @NotNull
    public static org.bukkit.inventory.ItemStack addGlow(org.bukkit.inventory.ItemStack item) {
        if(item == null){
            return null;
        }
        if ((item.hasItemMeta()) &&
                (item.getItemMeta().hasEnchants())) {
            return item;
        }

        net.minecraft.server.v1_7_R4.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = null;
        if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        }
        if (tag == null) {
            tag = nmsStack.getTag();
        }
        NBTTagList enchant = new NBTTagList();
        tag.set("ench", enchant);
        nmsStack.setTag(tag);
        return CraftItemStack.asCraftMirror(nmsStack);
    }
}


