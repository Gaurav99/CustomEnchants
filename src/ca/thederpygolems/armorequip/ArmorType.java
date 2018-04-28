package ca.thederpygolems.armorequip;

import com.peaches.customenchants.Support.Version;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;


public enum ArmorType {

    HELMET(5), CHESTPLATE(6), LEGGINGS(7), BOOTS(8);

    private final int slot;

    ArmorType(int slot) {
        this.slot = slot;
    }


    @Nullable
    public static ArmorType matchType(@Nullable final ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        if (itemStack.getType().name().toLowerCase().contains("skull")) {
            return HELMET;
        }
        if (Version.getVersion().comparedTo(Version.v1_9_R1) >= 0) {
            if (itemStack.getType() == Material.valueOf("ELYTRA")) {
                return CHESTPLATE;
            }
        }
        switch (itemStack.getType()) {
            case DIAMOND_HELMET:
            case GOLD_HELMET:
            case IRON_HELMET:
            case CHAINMAIL_HELMET:
            case LEATHER_HELMET:
                return HELMET;
            case DIAMOND_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case IRON_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case LEATHER_CHESTPLATE:
                return CHESTPLATE;
            case DIAMOND_LEGGINGS:
            case GOLD_LEGGINGS:
            case IRON_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case LEATHER_LEGGINGS:
                return LEGGINGS;
            case DIAMOND_BOOTS:
            case GOLD_BOOTS:
            case IRON_BOOTS:
            case CHAINMAIL_BOOTS:
            case LEATHER_BOOTS:
                return BOOTS;
            default:
                return null;
        }
    }

    public int getSlot() {
        return slot;
    }

}
