package com.peaches.customenchants.main;

import com.google.common.collect.Lists;
import com.peaches.customenchants.Support.Version;
import com.peaches.customenchants.Support.nms.*;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R1.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;

public class Utils implements Listener {
    public static final HashMap<String, String> desc = new HashMap<>();
    public static final HashMap<String, String> type = new HashMap<>();
    private final static int CENTER_PX = 154;
    private static Main plugin;

    public Utils(Main pl) {
        plugin = pl;
    }

    public static void sendParticle(Location loc, EnumParticle particle){
        if (Version.getVersion().equals(Version.v1_8_R1)) {
            NMS_v1_8_R1.sendParticle(EnumParticle.valueOf(particle.name()),
                    loc, 0.0F, 0.0F, 0.0F, 1.0F, 10);
        }
        if (Version.getVersion().equals(Version.v1_8_R2)) {
            NMS_v1_8_R2.sendParticle(net.minecraft.server.v1_8_R2.EnumParticle.valueOf(particle.name()),
                    loc, 0.0F, 0.0F, 0.0F, 1.0F, 10);
        }
        if (Version.getVersion().equals(Version.v1_8_R3)) {
            NMS_v1_8_R3.sendParticle(net.minecraft.server.v1_8_R3.EnumParticle.valueOf(particle.name()),
                    loc, 0.0F, 0.0F, 0.0F, 1.0F, 10);
        }
        if (Version.getVersion().equals(Version.v1_9_R1)) {
            NMS_v1_9_R1.sendParticle(net.minecraft.server.v1_9_R1.EnumParticle.valueOf(particle.name()),
                    loc, 0.0F, 0.0F, 0.0F, 1.0F, 10);
        }
        if (Version.getVersion().equals(Version.v1_9_R2)) {
            NMS_v1_9_R2.sendParticle(net.minecraft.server.v1_9_R2.EnumParticle.valueOf(particle.name()),
                    loc, 0.0F, 0.0F, 0.0F, 1.0F, 10);
        }
        if (Version.getVersion().equals(Version.v1_10_R1)) {
            NMS_v1_10_R1.sendParticle(net.minecraft.server.v1_10_R1.EnumParticle.valueOf(particle.name()),
                    loc, 0.0F, 0.0F, 0.0F, 1.0F, 10);
        }
        if (Version.getVersion().equals(Version.v1_11_R1)) {
            NMS_v1_11_R1.sendParticle(net.minecraft.server.v1_11_R1.EnumParticle.valueOf(particle.name()),
                    loc, 0.0F, 0.0F, 0.0F, 1.0F, 10);
        }
    }


    public static String sendCenteredMessage(String message){
        if(message == null || message.equals("")) return "";
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = true;
                continue;
            }else if(previousCode == true){
                previousCode = false;
                if(c == 'l' || c == 'L'){
                    isBold = true;
                    continue;
                }else isBold = false;
            }else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        return sb.toString() + message;
    }


    public static String NextPage() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Options.NextPage"));
    }

    public static String PreviousPage() {
        return ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("Options.PreviousPage"));
    }


    private static int getTotalExperience(Player player) {// https://gist.github.com/RichardB122/8958201b54d90afbc6f0
        int experience;
        int level = player.getLevel();
        if(level >= 0 && level <= 15) {
            experience = (int) Math.ceil(Math.pow(level, 2) + (6 * level));
            int requiredExperience = 2 * level + 7;
            double currentExp = Double.parseDouble(Float.toString(player.getExp()));
            experience += Math.ceil(currentExp * requiredExperience);
            return experience;
        }else if(level > 15 && level <= 30) {
            experience = (int) Math.ceil((2.5 * Math.pow(level, 2) - (40.5 * level) + 360));
            int requiredExperience = 5 * level - 38;
            double currentExp = Double.parseDouble(Float.toString(player.getExp()));
            experience += Math.ceil(currentExp * requiredExperience);
            return experience;
        }else {
            experience = (int) Math.ceil(((4.5 * Math.pow(level, 2) - (162.5 * level) + 2220)));
            int requiredExperience = 9 * level - 158;
            double currentExp = Double.parseDouble(Float.toString(player.getExp()));
            experience += Math.ceil(currentExp * requiredExperience);
            return experience;
        }
    }


    public static void RemoveXP(@NotNull Player player, int amount) {
            if(Version.getVersion().getVersionInteger() >= 181) {
                int total = getTotalExperience(player) - amount;
                player.setTotalExperience(0);
                player.setTotalExperience(total);
                player.setLevel(0);
                player.setExp(0);
                for(; total > player.getExpToLevel(); ) {
                    total -= player.getExpToLevel();
                    player.setLevel(player.getLevel() + 1);
                }
                float xp = (float) total / (float) player.getExpToLevel();
                player.setExp(xp);
            }else {
                        if(getTotalExperience(player) == amount) {
                            player.giveExp(-(amount - 1));
                            return;
                }
                player.giveExp(-amount);
            }
        }

    public static boolean Vault() {
        return plugin.getConfig().getString("Options.Payment").equalsIgnoreCase("vault");
    }

    public static boolean Xp(){
        return plugin.getConfig().getString("Options.Payment").equalsIgnoreCase("xp");
    }

    public static boolean XpLevels() {
        return plugin.getConfig().getString("Options.Payment").equalsIgnoreCase("xplevels");
    }

    public static String prefix() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Options.Prefix"));
    }

    public static String getTitle() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Options.Title"));
    }

    public static String RandomKey() {
        Random object = new Random();
        for (int counter = 1; counter <= 1; counter++) {
            int i = 1 + object.nextInt(plugin.getConfig().getKeys(false).size());
            int a = 0;
            for (String Map : plugin.getConfig().getKeys(false)) {
                a++;
                if (a == i) {
                    return Map;
                }
            }
        }
        return null;
    }

    public static Integer convertPowertoInt(String i) {
        if (i.equals("I")) {
            return 1;
        }
        if (i.equals("II")) {
            return 2;
        }
        if (i.equals("III")) {
            return 3;
        }
        if (i.equals("IV")) {
            return 4;
        }
        if (i.equals("X")) {
            return 5;
        }
        if (i.equals("XI")) {
            return 6;
        }
        if (i.equals("XII")) {
            return 7;
        }
        if (i.equals("VIII")) {
            return 8;
        }
        if (i.equals("IX")) {
            return 9;
        }
        if (i.equals("X")) {
            return 10;
        }
        return Integer.parseInt(i);
    }

    public static String convertPower(int i) {
        if (i <= 0) {
            return "I";
        }
        if (i == 1) {
            return "I";
        }
        if (i == 2) {
            return "II";
        }
        if (i == 3) {
            return "III";
        }
        if (i == 4) {
            return "IV";
        }
        if (i == 5) {
            return "V";
        }
        if (i == 6) {
            return "VI";
        }
        if (i == 7) {
            return "VII";
        }
        if (i == 8) {
            return "VIII";
        }
        if (i == 9) {
            return "IX";
        }
        if (i == 10) {
            return "X";
        }
        return i + "";
    }

    public static boolean hasOpenSlot(Inventory inv) {
        int open = 0;
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null) {
                open++;
            }
        }
        return open > 0;
    }

    public static boolean isCrystal(@Nullable ItemStack itm) {
        return itm != null && itm.getType().equals(Material.getMaterial(plugin.getConfig().getString("Options.EnchantCrystalItem"))) && itm.getItemMeta().hasLore();
    }

    public static String removeColor(String msg) {
        msg = ChatColor.stripColor(msg);
        return msg;
    }

    public static String format(@NotNull String format, Object... objects) {
        String ret = java.text.MessageFormat.format(format, objects);
        return ChatColor.translateAlternateColorCodes('&', ret);
    }

    public static String color(@NotNull String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    @NotNull
    public static ItemStack makeItem(@NotNull Material material, int amount, int type) {
        ItemStack item = new ItemStack(material, amount, (short) type);
        ItemMeta m = item.getItemMeta();
        item.setItemMeta(m);
        return item;
    }

    @NotNull
    public static ItemStack makeItem(@NotNull Material material, int amount, int type, @NotNull String name) {
        ItemStack item = new ItemStack(material, amount, (short) type);
        ItemMeta m = item.getItemMeta();
        m.setDisplayName(color(name));
        item.setItemMeta(m);
        return item;
    }

    @NotNull
    public static ItemStack makeItem(String type, int amount) {
        int ty = 0;
        if (type.contains(":")) {
            String[] b = type.split(":");
            type = b[0];
            ty = Integer.parseInt(b[1]);
        }
        Material m = Material.matchMaterial(type);
        ItemStack item = new ItemStack(m, amount, (short) ty);
        ItemMeta me = item.getItemMeta();
        item.setItemMeta(me);
        return item;
    }

    @NotNull
    public static ItemStack createCrystalItem(String enchant, String level, String type) {
        ItemStack itm = new ItemStack(Material.getMaterial(plugin.getConfig().getString("Options.EnchantCrystalItem")));
        ItemMeta meta = itm.getItemMeta();
        meta.setDisplayName(
                ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Options.CrystalNameFormat"))
                        + enchant + " " + level.toUpperCase());
        List<String> lore = Lists.newArrayList();
        lore.add(format(plugin.getConfig().getString("Options.CrystalLoreFormat") + type + " Enchant"));
        if (plugin.getConfig().getList("Options.EnchantCrystalLore") != null) {
            for (Object a : plugin.getConfig().getList("Options.EnchantCrystalLore")) {
                if (a != null) {
                    if (plugin.getConfig().getString("Description." + enchant + level.toUpperCase()) != null) {
                        lore.add(ChatColor.translateAlternateColorCodes('&', a.toString()).replace("%Description%",
                                plugin.getConfig().getString("Description." + enchant + level.toUpperCase())));
                    }
                }
            }

        }
        meta.setLore(lore);
        itm.setItemMeta(meta);
        return itm;
    }

    public static Boolean hasenchant(String Enchant, @Nullable ItemStack item) {
        if (item != null) {
            if (item.hasItemMeta()) {
                if (item.getItemMeta().hasLore()) {
                    List<String> lore = item.getItemMeta().getLore();
                    for (String l : lore) {
                        if (l.equals(ChatColor.translateAlternateColorCodes('&',
                                plugin.getConfig().getString("Options.Tier1LoreFormat")) + Enchant)) {
                            return true;
                        }
                        if (l.equals(ChatColor.translateAlternateColorCodes('&',
                                plugin.getConfig().getString("Options.Tier2LoreFormat")) + Enchant)) {
                            return true;
                        }
                        if (l.equals(ChatColor.translateAlternateColorCodes('&',
                                plugin.getConfig().getString("Options.Tier3LoreFormat")) + Enchant)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static ItemStack getair() {
        return new ItemStack(Material.AIR);
    }

    @NotNull
    public static ItemStack removeLore(ItemStack item, String i) {
        ItemMeta m = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>(item.getItemMeta().getLore());
        m.setLore(lore);
        item.setItemMeta(m);
        return item;
    }

    @NotNull
    public static ItemStack addLore(ItemStack item, @NotNull String i) {
        ArrayList<String> lore = new ArrayList<>();
        ArrayList<String> removelore = new ArrayList<>();
        ItemMeta m = item.getItemMeta();
        if ((item.getItemMeta() != null) && (item.getItemMeta().hasLore())) {
            lore.addAll(m.getLore());
        }
        String[] enchant = i.split(" ");
        if(plugin.Abilities_List.contains(ChatColor.stripColor(enchant[0]))){
            for(String l : plugin.Abilities_List){
                for(String l1 : lore){
                    if(ChatColor.stripColor(l1).contains(ChatColor.stripColor(l))){
                        removelore.add(l1);
                    }
                }
            }
        }
        for(String l : removelore){
            if(lore.contains(l)){
                lore.remove(l);
            }
        }
        if (plugin.getConfig().getConfigurationSection("MaxPower." + enchant[0]) != null) {
            for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower." + enchant[0]); counter++) {
                if (lore.contains(
                        plugin.getConfig().getString("Options.Tier1LoreFormat") + enchant[0] + convertPower(counter))) {
                    lore.remove(plugin.getConfig().getString("Options.Tier1LoreFormat") + enchant[0]
                            + convertPower(counter));
                }
                if (lore.contains(
                        plugin.getConfig().getString("Options.Tier2LoreFormat") + enchant[0] + convertPower(counter))) {
                    lore.remove(plugin.getConfig().getString("Options.Tier2LoreFormat") + enchant[0]
                            + convertPower(counter));
                }
                if (lore.contains(
                        plugin.getConfig().getString("Options.Tier3LoreFormat") + enchant[0] + convertPower(counter))) {
                    lore.remove(plugin.getConfig().getString("Options.Tier3LoreFormat") + enchant[0]
                            + convertPower(counter));
                }
            }
        }
        java.io.File customenchants = new java.io.File("plugins//CustomEnchants//CustomEnchants.yml");
        YamlConfiguration enchants = YamlConfiguration.loadConfiguration(customenchants);
        if (enchants.getConfigurationSection("Enchantments") != null) {
            for (String Enchant : enchants.getConfigurationSection("Enchantments").getKeys(false)) {
                for (int counter = 0; counter <= enchants.getInt("Enchantments." + Enchant + ".MaxPower"); counter++) {
                    if (lore.contains(plugin.getConfig().getString("Options.Tier1LoreFormat") + enchant[0]
                            + convertPower(counter))) {
                        lore.remove(plugin.getConfig().getString("Options.Tier1LoreFormat") + enchant[0]
                                + convertPower(counter));
                    }
                    if (lore.contains(plugin.getConfig().getString("Options.Tier2LoreFormat") + enchant[0]
                            + convertPower(counter))) {
                        lore.remove(plugin.getConfig().getString("Options.Tier2LoreFormat") + enchant[0]
                                + convertPower(counter));
                    }
                    if (lore.contains(plugin.getConfig().getString("Options.Tier3LoreFormat") + enchant[0]
                            + convertPower(counter))) {
                        lore.remove(plugin.getConfig().getString("Options.Tier3LoreFormat") + enchant[0]
                                + convertPower(counter));
                    }
                }
            }
        }
        if (plugin.getConfig().getList("Tiers.Tier2").contains(ChatColor.stripColor(i.replace(" ", "")))) {
            if (!lore.contains(plugin.getConfig().getString("Options.Tier2LoreFormat") + i)) {
                lore.add(color(plugin.getConfig().getString("Options.Tier2LoreFormat") + i));
            }
        } else if (plugin.getConfig().getList("Tiers.Tier3").contains(ChatColor.stripColor(i.replace(" ", "")))) {
            if (!lore.contains(plugin.getConfig().getString("Options.Tier3LoreFormat") + i)) {
                lore.add(color(plugin.getConfig().getString("Options.Tier3LoreFormat") + i));
            }
        } else if (!lore.contains(plugin.getConfig().getString("Options.Tier1LoreFormat") + i)) {
            lore.add(color(plugin.getConfig().getString("Options.Tier1LoreFormat") + i));
        }
        if (m == null) {
            item.getItemMeta().setLore(lore);
            return item;
        }
        m.setLore(lore);
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack addGlow(@NotNull ItemStack item) {
        if (Version.getVersion().equals(Version.v1_7_R4)) {
            return NMS_v1_7_R4.addGlow(item);
        }
        if (Version.getVersion().equals(Version.v1_8_R1)) {
            return NMS_v1_8_R1.addGlow(item);
        }
        if (Version.getVersion().equals(Version.v1_8_R2)) {
            return NMS_v1_8_R2.addGlow(item);
        }
        if (Version.getVersion().equals(Version.v1_8_R3)) {
            return com.peaches.customenchants.Support.nms.NMS_v1_8_R3.addGlow(item);
        }
        if (Version.getVersion().equals(Version.v1_9_R1)) {
            return com.peaches.customenchants.Support.nms.NMS_v1_9_R1.addGlow(item);
        }
        if (Version.getVersion().equals(Version.v1_9_R2)) {
            return com.peaches.customenchants.Support.nms.NMS_v1_9_R2.addGlow(item);
        }
        if (Version.getVersion().equals(Version.v1_10_R1)) {
            return com.peaches.customenchants.Support.nms.NMS_v1_10_R1.addGlow(item);
        }
        if (Version.getVersion().equals(Version.v1_11_R1)) {
            return NMS_v1_11_R1.addGlow(item);
        }
        if (Version.getVersion().equals(Version.v1_12_R1)) {
            return NMS_v1_12_R1.addGlow(item);
        }
        return item;
    }

    public static boolean Tier() {
        return plugin.getConfig().getBoolean("Options.TierGUI");
    }

    public static int getIgnitionI() {
        return plugin.getConfig().getInt("Chances.IgnitionI");
    }

    public static int getParalyzeI() {
        return plugin.getConfig().getInt("Chances.ParalyzeI");
    }

    public static int getParalyzeII() {
        return plugin.getConfig().getInt("Chances.ParalyzeII");
    }

    public static int getBlindnessI() {
        return plugin.getConfig().getInt("Chances.BlindnessI");
    }

    public static int getBlindnessII() {
        return plugin.getConfig().getInt("Chances.BlindnessII");
    }

    public static int getPoisonI() {
        return plugin.getConfig().getInt("Chances.PoisonI");
    }

    public static int getPoisonII() {
        return plugin.getConfig().getInt("Chances.PoisonII");
    }

    public static int getWitherI() {
        return plugin.getConfig().getInt("Chances.WitherI");
    }

    public static int getWitherII() {
        return plugin.getConfig().getInt("Chances.WitherII");
    }

    public static int getNauseaI() {
        return plugin.getConfig().getInt("Chances.NauseaI");
    }

    public static int getSlownessI() {
        return plugin.getConfig().getInt("Chances.SlownessI");
    }

    public static int getSlownessII() {
        return plugin.getConfig().getInt("Chances.SlownessII");
    }

    public static int getLifestealI() {
        return plugin.getConfig().getInt("Chances.LifestealI");
    }

    public static int getLifestealII() {
        return plugin.getConfig().getInt("Chances.LifestealII");
    }

    public static int getFlareI() {
        return plugin.getConfig().getInt("Chances.FlareI");
    }

    public static int getLocksmithI() {
        return plugin.getConfig().getInt("Chances.LocksmithI");
    }

    public static int getLocksmithII() {
        return plugin.getConfig().getInt("Chances.LocksmithII");
    }

    public static int getCursedI() {
        return plugin.getConfig().getInt("Chances.CursedI");
    }

    public static int getCursedII() {
        return plugin.getConfig().getInt("Chances.CursedII");
    }

    private static int getTierSlot(int i) {
        if(!plugin.getConfig().contains("Options.Tier"+i+"Slot")){
            plugin.getConfig().set("Options.Tier"+i+"Slot", 1);
            plugin.saveConfig();
            plugin.reloadConfig();
        }
        return plugin.getConfig().getInt("Options.Tier"+i+"Slot");
    }

    public static int getTierCost(int i) {
        if(!plugin.getConfig().contains("Options.Tier"+i+"Cost")){
            plugin.getConfig().set("Options.Tier"+i+"Cost", 10);
            plugin.saveConfig();
            plugin.reloadConfig();
        }
        return plugin.getConfig().getInt("Options.Tier"+i+"Cost");
    }

    private static String getTierName(int i){
        if(!plugin.getConfig().contains("Options.Tier"+i+"Name")){
            plugin.getConfig().set("Options.Tier"+i+"Name", "Tier"+i);
            plugin.saveConfig();
            plugin.reloadConfig();
        }
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Options.Tier"+i+"Name"));
    }

    public static ItemStack getTier(int i){
        if(!plugin.getConfig().contains("Options.Tier"+i+"Item")){
            plugin.getConfig().set("Options.Tier"+i+"Item", "ENCHANTED_BOOK");
            plugin.saveConfig();
            plugin.reloadConfig();
        }
        ItemStack item = makeItem(Material.getMaterial(plugin.getConfig().getString("Options.Tier"+i+"Item")), 1,
                plugin.getConfig().getInt("Options.Tier"+i+"Meta"), getTierName(i));
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("Options.TierCostFormat").replace("%Cost%", getTierCost(i) + "")));
        meta.setLore(lore);
        item.setItemMeta(meta);
        if(!plugin.getConfig().contains("Options.Tier"+i+"Glow")){
            plugin.getConfig().set("Options.Tier"+i+"Glow", true);
            plugin.saveConfig();
            plugin.reloadConfig();
        }
        if (plugin.getConfig().getBoolean("Options.Tier"+i+"Glow")) {
            return addGlow(item);
        }
        return item;
    }

    @NotNull
    private static ItemStack getDisplayItem(String enchant, String level, String type, String Desc) {
        ItemStack itm = new ItemStack(Material.getMaterial(plugin.getConfig().getString("Options.EnchantCrystalItem")));
        ItemMeta meta = itm.getItemMeta();
        meta.setDisplayName(
                ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Options.CrystalNameFormat"))
                        + enchant + " " + level);
        List<String> lore = Lists.newArrayList();
        lore.add(format(plugin.getConfig().getString("Options.CrystalLoreFormat") + type + " Enchant"));
        lore.add(format(plugin.getConfig().getString("Options.CrystalLoreFormat") + Desc));
        meta.setLore(lore);
        itm.setItemMeta(meta);
        return itm;
    }

    private static void takeXP(Player player, int amount, int start) {
        int xptotal = start - amount;
        player.setTotalExperience(xptotal);
        player.setLevel(0);
        while (xptotal > player.getExpToLevel()) {
            xptotal -= player.getExpToLevel();
            player.setLevel(player.getLevel() + 1);
        }
        float xp = xptotal / player.getExpToLevel();
        player.setExp(xp);
    }

    public static Inventory Gkits(@NotNull Player p) {
        File gkits = new File("plugins//CustomEnchants//GKits.yml");
        YamlConfiguration gkit = YamlConfiguration.loadConfiguration(gkits);
        Inventory inv = Bukkit.createInventory(null, 45, getTitle());
        for (int i = 0; i < 45; i++) {
            if(Material.getMaterial(plugin.getConfig().getString("Options.GkitsBackgroundItem").toUpperCase())!= null){
                inv.setItem(i, makeItem(Material.getMaterial(plugin.getConfig().getString("Options.GkitsBackgroundItem").toUpperCase()), 1, plugin.getConfig().getInt("Options.GkitsBackgroundMeta"), " "));
            }
        }
        for (String Key : gkit.getKeys(false)) {
            ArrayList<String> Lore = new ArrayList<>();
            ItemStack item = makeItem(Material.getMaterial(gkit.getString(Key + ".Item").toUpperCase()), 1, 0,
                    ChatColor.translateAlternateColorCodes('&', gkit.getString(Key + ".Name")));
            Lore.add(ChatColor.GREEN + "Click to redeem this kit!");
            for (String name : plugin.Gkits.keySet()) {
                String[] name1 = name.split(":");
                if ((name1[0].equals(p.getName())) && (name1[1].equals(Key))) {
                    int i = plugin.Gkits.get(name);
                    Lore.clear();
                    Lore.add(ChatColor.RED + "" + ChatColor.BOLD + "Cooldown: " + Integer.toString(i / 86400) + " Days "
                            + Integer.toString(i % 86400 / 3600) + " Hours " + Integer.toString(i % 3600 / 60)
                            + " Minutes ");
                }
            }

            if (!p.hasPermission("CustomEnchants.Gkits." + Key)) {
                Lore.clear();
                Lore.add(ChatColor.RED + "" + ChatColor.BOLD + "You do not have permission for this kit!");
            }
            ItemMeta meta = item.getItemMeta();
            meta.setLore(Lore);
            item.setItemMeta(meta);
            inv.setItem(gkit.getInt(Key + ".Slot"), item);
        }
        return inv;
    }

    public static Inventory showInventory(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, getTitle());
        for (int i = 0; i < 27; i++) {
            inv.setItem(i, makeItem(Material.STAINED_GLASS_PANE, 1, 9, " "));
        }
        for (int i = 0; i < 9; i++) {
            inv.setItem(i + 9, makeItem(Material.STAINED_GLASS_PANE, 1, 3, " "));
        }
        inv.setItem(10, makeItem(Material.STAINED_GLASS_PANE, 1, 11, " "));
        inv.setItem(12, makeItem(Material.STAINED_GLASS_PANE, 1, 11, " "));
        inv.setItem(14, makeItem(Material.STAINED_GLASS_PANE, 1, 11, " "));
        inv.setItem(16, makeItem(Material.STAINED_GLASS_PANE, 1, 11, " "));

        for (int i = 1; i <= plugin.getConfig().getInt("Options.TierAmount"); i++) {
            inv.setItem(getTierSlot(i) -1, getTier(i));
        }
        return inv;
    }

    public static Inventory showList(int no) {
        Set<String> stringSet = plugin.getConfig().getConfigurationSection("Description").getKeys(false);
        for (String type : stringSet) {
            desc.put(type, plugin.getConfig().getString("Description." + type));
        }

        int i = 0;
        Inventory inv = Bukkit.createInventory(null, 54, getTitle());
        for (String Enchant : type.keySet()) {
            if (plugin.getConfig().contains("MaxPower." + Enchant)) {
                for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower." + Enchant); counter++) {

                    i++;
                    if (i > 45 * (no - 1)) {
                        if(plugin.getConfig().getBoolean("Enabled."+Enchant+convertPower(counter))){
                            inv.addItem(getDisplayItem(plugin.getConfig().getString("Translate." + Enchant),
                                    convertPower(counter), type.get(Enchant), desc.get(Enchant + convertPower(counter))));
                        }
                    }
                }

            } else {
                if (plugin.getConfig().getBoolean("Enabled." + Enchant + "I")) {
                    i++;
                    if (i > 45 * (no - 1)) {
                        inv.addItem(getDisplayItem(plugin.getConfig().getString("Translate." + Enchant), "I",
                                type.get(Enchant), desc.get(Enchant + "I")));
                    }
                }
            }

        }
        File customenchants = new File("plugins//CustomEnchants//CustomEnchants.yml");
        YamlConfiguration enchants = YamlConfiguration.loadConfiguration(customenchants);
        if (enchants.getConfigurationSection("Enchantments") != null) {
            for (String Enchant : enchants.getConfigurationSection("Enchantments").getKeys(false)) {
                for (int counter = 1; counter <= enchants.getInt("Enchantments." + Enchant + ".MaxPower"); counter++) {
                    enchants.getBoolean("Enchantments." + Enchant + ".Enabled");

                    if (i > 45 * (no - 1)) {
                        inv.addItem(getDisplayItem(Enchant, convertPower(counter),
                                enchants.getString("Enchantments." + Enchant + ".Options.ItemsEnchantable"),
                                enchants.getString("Enchantments." + Enchant + ".Description")));
                    }
                }
            }
        }

        inv.setItem(45, new ItemStack(Material.AIR));
        inv.setItem(46, new ItemStack(Material.AIR));
        inv.setItem(47, makeItem(Material.STAINED_GLASS_PANE, 1, 14, PreviousPage()));
        inv.setItem(48, new ItemStack(Material.AIR));
        inv.setItem(49, new ItemStack(Material.AIR));
        inv.setItem(50, new ItemStack(Material.AIR));
        inv.setItem(51, makeItem(Material.STAINED_GLASS_PANE, 1, 5, NextPage()));
        inv.setItem(52, new ItemStack(Material.AIR));
        inv.setItem(53, new ItemStack(Material.AIR));
        return inv;
    }

    public static String getOrigionalEnchant(String Enchant){
        int length = Enchant.length();
        String newEnchant = Enchant;
        for (int counter1 = 1; counter1 <= length; counter1++) {
            length = newEnchant.length();
            newEnchant = Enchant.substring(0, length - 1);
            Bukkit.broadcastMessage(newEnchant);
            for(String e : type.keySet()){
                if(e.toLowerCase().equalsIgnoreCase(newEnchant.toLowerCase())){
                    return newEnchant;
                }
            }
        }
        return null;
    }

    public static Inventory showTierList(int no, int Tier) {
        List<?> Enchants = plugin.getConfig().getList("Tiers.Tier"+Tier);
        Set<String> stringSet = plugin.getConfig().getConfigurationSection("Description").getKeys(false);
        for (String type : stringSet) {
            desc.put(type, plugin.getConfig().getString("Description." + type));
        }

        int i = 0;
        Inventory inv = Bukkit.createInventory(null, 54, getTitle());
        for (String Enchant : type.keySet()) {
            if (plugin.getConfig().contains("MaxPower." + Enchant)) {
                for (int counter = 1; counter <= plugin.getConfig().getInt("MaxPower." + Enchant); counter++) {

                    i++;
                    if (i > 45 * (no - 1)) {
                        if(plugin.getConfig().getBoolean("Enabled."+Enchant+convertPower(counter))){
                            if(Enchants.contains(Enchant+convertPower(counter)) || Enchants.contains(plugin.getConfig().getString("Translate." + Enchant)+convertPower(counter))){
                                inv.addItem(getDisplayItem(plugin.getConfig().getString("Translate." + Enchant),
                                        convertPower(counter), type.get(Enchant), desc.get(Enchant + convertPower(counter))));
                            }
                        }
                    }
                }

            } else {
                if (plugin.getConfig().getBoolean("Enabled." + Enchant + "I")) {
                    i++;
                    if (i > 45 * (no - 1)) {

                        if(Enchants.contains(Enchant+"I") || Enchants.contains(plugin.getConfig().getString("Translate." + Enchant)+"I")) {
                            inv.addItem(getDisplayItem(plugin.getConfig().getString("Translate." + Enchant), "I",
                                    type.get(Enchant), desc.get(Enchant + "I")));
                        }
                    }
                }
            }

        }
        File customenchants = new File("plugins//CustomEnchants//CustomEnchants.yml");
        YamlConfiguration enchants = YamlConfiguration.loadConfiguration(customenchants);
        if (enchants.getConfigurationSection("Enchantments") != null) {
            for (String Enchant : enchants.getConfigurationSection("Enchantments").getKeys(false)) {
                for (int counter = 1; counter <= enchants.getInt("Enchantments." + Enchant + ".MaxPower"); counter++) {
                    enchants.getBoolean("Enchantments." + Enchant + ".Enabled");

                    if (i > 45 * (no - 1)) {
                        inv.addItem(getDisplayItem(Enchant, convertPower(counter),
                                enchants.getString("Enchantments." + Enchant + ".Options.ItemsEnchantable"),
                                enchants.getString("Enchantments." + Enchant + ".Description")));
                    }
                }
            }
        }

        inv.setItem(45, new ItemStack(Material.AIR));
        inv.setItem(46, new ItemStack(Material.AIR));
        inv.setItem(47, makeItem(Material.STAINED_GLASS_PANE, 1, 14, PreviousPage()));
        inv.setItem(48, new ItemStack(Material.AIR));
        inv.setItem(49, new ItemStack(Material.AIR));
        inv.setItem(50, new ItemStack(Material.AIR));
        inv.setItem(51, makeItem(Material.STAINED_GLASS_PANE, 1, 5, NextPage()));
        inv.setItem(52, new ItemStack(Material.AIR));
        inv.setItem(53, new ItemStack(Material.AIR));
        return inv;
    }

    public static Inventory Tinkerer(){
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', ConfigManager.getInstance().getTinker().getString("Options.Title")));
        inv.setItem(0, makeItem(Material.STAINED_GLASS_PANE, 1, 14, ChatColor.translateAlternateColorCodes('&', ConfigManager.getInstance().getTinker().getString("Options.DeclineTrade"))));
        ArrayList<Integer> slots = new ArrayList<>();
        slots.add(4);
        slots.add(13);
        slots.add(22);
        slots.add(31);
        slots.add(40);
        slots.add(49);
        for(int i : slots) {
            inv.setItem(i, makeItem(Material.STAINED_GLASS_PANE, 1, 0, "&f"));
        }
        inv.setItem(8, makeItem(Material.STAINED_GLASS_PANE, 1, 5, ChatColor.translateAlternateColorCodes('&', ConfigManager.getInstance().getTinker().getString("Options.AcceptTrade"))));
        return inv;
    }
}
