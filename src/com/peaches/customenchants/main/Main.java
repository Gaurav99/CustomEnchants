package com.peaches.customenchants.main;

import ca.thederpygolems.armorequip.ArmorListener;
import com.peaches.customenchants.Commands.CustomEnchants;
import com.peaches.customenchants.Commands.Tinker;
import com.peaches.customenchants.Commands.gkits;
import com.peaches.customenchants.Support.*;
import com.peaches.customenchants.Support.nms.*;
import com.peaches.customenchants.api.API;
import com.peaches.customenchants.events.*;
import com.peaches.customenchants.listeners.CrystalUse;
import com.peaches.customenchants.listeners.GkitsListner;
import com.peaches.customenchants.listeners.TierChoose;
import com.peaches.customenchants.listeners.TinkererListner;
import net.minecraft.server.v1_8_R1.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@SuppressWarnings("SuspiciousMethodCalls")
public class Main extends org.bukkit.plugin.java.JavaPlugin implements Listener {
    private final HashMap<Projectile, Integer> Target = new HashMap<>();
    public final ArrayList<Projectile> Flare = new ArrayList<>();
    public final HashMap<String, Integer> Gkits = new HashMap<>();
    public final HashMap<String, Integer> FuryData = new HashMap<>();
    public final HashMap<String, Integer> FuryTime = new HashMap<>();
    private final HashMap<String, Integer> Penetrate = new HashMap<>();
    private final ArrayList<String> fly = new ArrayList<>();
    @NotNull
    private HashMap<Arrow, BukkitRunnable> arrowTask = new HashMap<>();
    private final HashMap<UUID, PotionEffectType> haste = new HashMap<>();
    private final ArrayList<String> snow = new ArrayList<>();
    private final HashMap<Location, BukkitRunnable> BlockTask = new HashMap<>();
    private final HashMap<Location, Material> BlockData = new HashMap<>();
//    public final HashMap<String, Integer>Abilities = new HashMap<>();
    public final HashMap<String, Integer>Stealth = new HashMap<>();
    public final ArrayList<String> Abilities_List = new ArrayList<>();

    public Main(Main pl) {
    }

    public Main() {
        PluginDescriptionFile pdf = getDescription();
    }

    public void onEnable() {
        saveDefaultConfig();
        checkconfig();
        createFile();
        ConfigManager.getInstance().setup(this);
        registerBow();
        registerTarget();
        registerSnow();
        registerHaste();
        JacketFix();
        Inventory();
        Inventory1();
        GkitsCooldown();
        Stealth();
        Saviour();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        getCommand("gkits").setExecutor(new gkits(this));
        getCommand("Tinkerer").setExecutor(new Tinker());
        getCommand("Tinker").setExecutor(new Tinker());
        getCommand("customenchants").setExecutor(new CustomEnchants(this));
        Version.getVersion();
        Utils.type.put("Speed", "Boots");
        Utils.type.put("Jump", "Boots");
        Utils.type.put("Jacket", "Armor");
        Utils.type.put("Coins", "Weapons");
        Utils.type.put("Cursed", "Weapons");
        Utils.type.put("Assassinate", "Weapons");
        Utils.type.put("Nausea", "Weapons");
        Utils.type.put("Haste", "Tools");
        Utils.type.put("Penetrate", "Weapons");
        Utils.type.put("Evade", "Chestplate");
        Utils.type.put("Paralyze", "Weapons");
        Utils.type.put("Slowness", "Weapons");
        Utils.type.put("Reborn", "Weapons");
        Utils.type.put("Blindness", "Weapons");
        Utils.type.put("Inquisitive", "Weapons");
        Utils.type.put("Fury", "Weapons");
        Utils.type.put("Poison", "Weapons");
        Utils.type.put("Wither", "Weapons");
        Utils.type.put("Hardened", "Global");
        Utils.type.put("Multishot", "Bow");
        Utils.type.put("Lifesteal", "Weapons");
        Utils.type.put("Rage", "Armor");
        Utils.type.put("Infusion", "Tools");
        Utils.type.put("Decapitate", "Weapons");
        Utils.type.put("Visionary", "Helmet");
        Utils.type.put("Netherskin", "Chestplate");
        Utils.type.put("Ignition", "Chestplate");
        Utils.type.put("Replenish", "Helmet");
        Utils.type.put("Blazingtouch", "Tools");
        Utils.type.put("Aquatic", "Helmet");
        Utils.type.put("Frosty", "Boots");
        Utils.type.put("Flare", "Bow");
        Utils.type.put("Wings", "Boots");
        Utils.type.put("Saviour", "Chestplate");
        Utils.type.put("Leader", "Chestplate");
        Utils.type.put("Resistance", "Chestplate");
        Utils.type.put("Enlightened", "Chestplate");
        Utils.type.put("Cook", "Rod");
        Utils.type.put("Catch", "Rod");
        Utils.type.put("Frostbite", "Weapons");
        Utils.type.put("Headshot", "Bow");
        Utils.type.put("Warden", "Sword");
        Utils.type.put("Enderforged", "Chestplate");
        Utils.type.put("Stealth", "Chestplate");
        Utils.type.put("ThunderusBlow", "Sword");
        Abilities_List.add(getConfig().getString("Translate.Enderforged"));
        Abilities_List.add(getConfig().getString("Translate.Stealth"));
        registerEvents();
        for (String Enchant : Utils.type.keySet()) {
            for (int counter = 0; counter <= getConfig().getInt("MaxPower." + Enchant); counter++) {
                if (!getConfig().contains("Enabled." + Enchant + Utils.convertPower(counter))) {
                    getConfig().set("Enabled." + Enchant + Utils.convertPower(counter), Boolean.TRUE);
                }
            }
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            if ((p.getInventory().getBoots() != null) && (p.getInventory().getBoots().getItemMeta() != null)
                    && (p.getInventory().getBoots().getItemMeta().getLore() != null)) {

                if (Utils.hasenchant(getConfig().getString("Translate.Frosty") + " I", p.getInventory().getBoots())) {
                    snow.add(p.getName());
                }
            }

        }
        loadbal();
        try {
            URL checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=38733");
            URLConnection con = checkURL.openConnection();
            String newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if ((Double.parseDouble(getDescription().getVersion()) < Double.parseDouble(newVersion))) {
                update();
            }
        } catch (IOException ignored) {
        }
        reloadConfig();
        System.out.print("-------------------------------");
        System.out.print("");
        System.out.print("CustomEnchants Enabled!");
        System.out.print("");
        System.out.print("-------------------------------");
    }

    public void onDisable() {
        for (Location loc : BlockData.keySet()) {
            loc.getBlock().setType(BlockData.get(loc));
            BlockData.remove(loc);
            BlockTask.get(loc).cancel();
            BlockTask.remove(loc);
        }
        System.out.print("-------------------------------");
        System.out.print("");
        System.out.print("CustomEnchants Disabled!");
        System.out.print("");
        System.out.print("-------------------------------");
        savebal();
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new Utils(this), this);
        pm.registerEvents(new API(this), this);
        pm.registerEvents(new CrystalUse(this), this);
        pm.registerEvents(new TierChoose(this), this);
        pm.registerEvents(new GkitsListner(this), this);
        pm.registerEvents(new TinkererListner(this), this);
        pm.registerEvents(new ArmorListener(), this);
        pm.registerEvents(new ArmorEquipt(this), this);
        pm.registerEvents(new PlayerDeathEvent(this), this);
        pm.registerEvents(new PlayerDamageEvent(this), this);
        pm.registerEvents(new PlayerFoodChangeEvent(this), this);
        pm.registerEvents(new OnShootBow(this), this);
        pm.registerEvents(new OnBlockBreak(this), this);
        pm.registerEvents(new CrystalSaftey(this), this);
        pm.registerEvents(new PlayerItemDamagetake(this), this);
        pm.registerEvents(new PlayerFish(this), this);
        pm.registerEvents(new ActionEvent(this), this);
        if (Support.AAC()) {
            pm.registerEvents(new AACSupport(), this);
        }
        if (Support.Spartan()) {
            pm.registerEvents(new SpartanSupport(), this);
        }
        if (Support.PAC()) {
            pm.registerEvents(new PACSupport(), this);
        }
    }

    private void addblock(Material a, Material m, @NotNull Location loc, Integer time, boolean proximity) {
        if (!BlockData.containsKey(loc) && !BlockTask.containsKey(loc)) {
            if (loc.getBlock().getType().equals(a)) {
                return;
            }
            loc.getBlock().setType(a);
            BlockData.put(loc, m);
            BlockTask.put(loc, new BukkitRunnable() {
                public void run() {
                    if (proximity) {
                        Boolean i = false;
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (!(Math.sqrt(p.getLocation().distanceSquared(
                                    loc)) < 4.0D)) {
                                i = true;
                            }
                        }
                        if (i) {
                            if(loc != null){
                                loc.getBlock().setType(BlockData.get(loc));
                                BlockData.remove(loc);
                                BlockTask.get(loc).cancel();
                                BlockTask.remove(loc);
                            }
                        }
                    } else {
                        if(loc != null){
                            loc.getBlock().setType(BlockData.get(loc));
                            BlockData.remove(loc);
                            BlockTask.get(loc).cancel();
                            BlockTask.remove(loc);
                        }
                    }
                }
            });
            BlockTask.get(loc)
                    .runTaskTimer(Bukkit.getPluginManager()
                            .getPlugin("CustomEnchants"), time, time);
        }
    }

    public void FrostBite(@NotNull Player p, int time) {
        if (Support.canBreakBlock(p, p.getLocation().clone().add(0, -1, 0).getBlock())) {
            addblock(Material.PACKED_ICE, p.getLocation().clone().add(0, -1, 0).getBlock().getType(), p.getLocation().clone().add(0, -1, 0), time, false);
        }
        if (Support.canBreakBlock(p, p.getLocation().clone().add(0, 2, 0).getBlock())) {
            addblock(Material.PACKED_ICE, p.getLocation().clone().add(0, 2, 0).getBlock().getType(), p.getLocation().clone().add(0, 2, 0), time, false);
        }
        if (Support.canBreakBlock(p, p.getLocation().clone().add(1, 0, 0).getBlock())) {
            addblock(Material.PACKED_ICE, p.getLocation().clone().add(1, 0, 0).getBlock().getType(), p.getLocation().clone().add(1, 0, 0), time, false);
        }
        if (Support.canBreakBlock(p, p.getLocation().clone().add(-1, 0, 0).getBlock())) {
            addblock(Material.PACKED_ICE, p.getLocation().clone().add(-1, 0, 0).getBlock().getType(), p.getLocation().clone().add(-1, 0, 0), time, false);
        }
        if (Support.canBreakBlock(p, p.getLocation().clone().add(0, 0, 1).getBlock())) {
            addblock(Material.PACKED_ICE, p.getLocation().clone().add(0, 0, 1).getBlock().getType(), p.getLocation().clone().add(0, 0, 1), time, false);
        }
        if (Support.canBreakBlock(p, p.getLocation().clone().add(0, 0, -1).getBlock())) {
            addblock(Material.PACKED_ICE, p.getLocation().clone().add(0, 0, -1).getBlock().getType(), p.getLocation().clone().add(0, 0, -1), time, false);
        }

    }

    public void addpenetrate(String player, int i) {
        Penetrate.put(player, i);
    }

    public void removepenetrate(String player) {
        if (Penetrate.containsKey(player)) {
            Penetrate.remove(player);
        }
    }

    public boolean containspenetrate(String player) {
        return Penetrate.containsKey(player);
    }

    public int getpenetrate(String player) {
        return Penetrate.get(player);
    }

    public void addfly(String player) {
        fly.add(player);
    }

    public void removefly(String player) {
        if (fly.contains(player)) {
            fly.remove(player);
        }
    }

    public boolean containsfly(String player) {
        return fly.contains(player);
    }

    private void Saviour() {

        boolean disable = true;
        for (int counter = 1; counter <= getConfig().getInt("MaxPower.Saviour"); counter++) {
            if (getConfig().getBoolean("Enabled.Saviour" + Utils.convertPower(counter))) {
                disable = false;
            }
        }
        if (disable) {
            return;
        }
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                for (int counter = 1; counter <= getConfig().getInt("MaxPower.Savior"); counter++) {
                    if (getConfig().getBoolean("Enabled.Savior" + Utils.convertPower(counter))) {
                        if (Utils.hasenchant(
                                getConfig().getString("Translate.Savior") + " " + Utils.convertPower(counter),
                                p.getInventory().getChestplate())) {

                            int radius = 4;
                            for (Entity en : p.getNearbyEntities(radius, radius, radius)) {
                                if (en instanceof Player) {
                                    Player o = (Player) en;
                                    if (Support.isFriendly(p, o)) {

                                        if (o.hasPotionEffect(PotionEffectType.REGENERATION)) {
                                            o.removePotionEffect(PotionEffectType.REGENERATION);
                                        }
                                        o.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5 * 20,
                                                counter - 1));
                                    }

                                }
                            }
                        }
                    }
                }
                for (int counter = 1; counter <= getConfig().getInt("MaxPower.Leader"); counter++) {
                    if (getConfig().getBoolean("Enabled.Leader" + Utils.convertPower(counter))) {
                        if (Utils.hasenchant(
                                getConfig().getString("Translate.Leader") + " " + Utils.convertPower(counter),
                                p.getInventory().getChestplate())) {

                            int radius = 4;
                            for (Entity en : p.getNearbyEntities(radius, radius, radius)) {
                                if (en instanceof Player) {
                                    Player o = (Player) en;
                                    if (Support.isFriendly(p, o)) {
                                        if (o.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
                                            o.removePotionEffect(PotionEffectType.FAST_DIGGING);
                                        }
                                        o.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 5 * 20,
                                                counter - 1));
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }, 0, 3 * 20);

    }

    private void update() {
        Support supp = new Support(this);
        supp.Update();
    }

    private void checkconfig() {
        File config1 = new File("plugins//CustomEnchants//config.yml");
        YamlConfiguration config2 = YamlConfiguration.loadConfiguration(config1);
        Configuration config = getConfig().getDefaults();
        for(String path : config.getKeys(false)){
            for(String path1 : config.getConfigurationSection(path).getKeys(false)){
                if(!config2.contains(path+"."+path1)){
                    config2.set(path+"."+path1, config.get(path+"."+path1));
                }
            }
        }
        try {
            config2.save(config1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadbal() {
        File gkitsCooldown = new File("plugins//CustomEnchants//GKitsCooldown.yml");
        YamlConfiguration gkit = YamlConfiguration.loadConfiguration(gkitsCooldown);
        for (String name : gkit.getKeys(false)) {
            Gkits.put(name, gkit.getInt(name));
            gkit.set(name, null);
        }
        try {
            gkit.save(gkitsCooldown);
        } catch (IOException ignored) {
        }
    }

    private void savebal() {
        File gkitsCooldown = new File("plugins//CustomEnchants//GKitsCooldown.yml");
        YamlConfiguration gkit = YamlConfiguration.loadConfiguration(gkitsCooldown);
        for (String name : Gkits.keySet()) {
            gkit.set(name, Gkits.get(name));
        }
        try {
            gkit.save(gkitsCooldown);
        } catch (IOException ignored) {
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createFile() {
        File gkitsCooldown = new File("plugins//CustomEnchants//GKitsCooldown.yml");
        if (!gkitsCooldown.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                gkitsCooldown.createNewFile();
            } catch (IOException ignored) {
            }
        }
        File gkits = new File("plugins//CustomEnchants//GKits.yml");
        if (!gkits.exists()) {
            try {
                gkits.createNewFile();
                YamlConfiguration gkit = YamlConfiguration.loadConfiguration(gkits);
                ArrayList<String> enchants = new ArrayList<>();
                enchants.add("DAMAGE_ALL 2");
                enchants.add("Wither II:30");
                gkit.set("Frosty.Item", "Snow_ball");
                gkit.set("Frosty.Name", "&c&lFrosty Kit");
                gkit.set("Frosty.Slot", 11);
                gkit.set("Frosty.Kit.1.Item", "Diamond_sword");
                gkit.set("Frosty.Kit.1.Name", "&b&lFrost Sword");
                gkit.set("Frosty.Kit.1.Amount", 1);
                gkit.set("Frosty.Kit.1.Enchants", enchants);
                gkit.save(gkits);
            } catch (IOException ignored) {
            }
        }
        File msg = new File("plugins//CustomEnchants//Messages.yml");
        if (!msg.exists()) {
            try {
                msg.createNewFile();
                YamlConfiguration messages = YamlConfiguration.loadConfiguration(msg);
                messages.set("NeedMoreXp", "&7 You Need More XP Levels");
                messages.set("GiveEnchant",
                        "&7 You have purchased a &c%Tier% &7enchant and recived a &c%Enchant% &7enchant!");
                messages.set("StartReload", "&7 Reloading Plugin...");
                messages.set("EndReload", "&7 Reload Complete took %ms%ms.");
                messages.set("NoPermission", "&7 Insufficient Permission!");
                messages.set("EnableEnchant", "&7 %Enchant% &7Enchant Enabled!");
                messages.set("DisableEnchant", "&7 %Enchant% &7Enchant Disabled!");
                messages.set("UnknownEnchant", "&7 Unknown Enchant!");
                messages.set("UnknownPlayer", "&7 Unknown Player!");
                messages.set("EnchantDisabled", "&7 That Enchant Is Disabled!");
                messages.set("GivePlayerEnchant", "&7 You gave &c%Player%&7 a &c%Enchant% &7enchant!");
                messages.set("MustBePlayer", "&7 You Bust be a player to do this command!");
                messages.set("ToMuchEnchants", "&7 There Are to many enchants accosiated with this item!");
                messages.set("UnknownGkit", "&7 The gkit %name% does not exist!");
                messages.set("GkitReset", "&7 You have reset the %name% Gkit for %player%!");
                messages.set("GkitNoCooldown", "&7 %player% has no cooldown for the %name% Gkit!");
                messages.set("Update", "&7 A new update was found! Version: %version%!");
                messages.set("NoItems", "&7 You have no items that can be enchanted with this crystal");
                messages.set("FlyEnabled", "&7 Your flight has been &cEnabled!");
                messages.set("FlyDisabled", "&7 Your flight has been &cDisabled!");
                messages.set("WaitMessage", "&7 You must wait &c%time% seconds!");
                messages.save(msg);
            } catch (IOException ignored) {
            }
        } else {
            YamlConfiguration messages = YamlConfiguration.loadConfiguration(msg);
            if (messages.getString("NeedMoreXp") == null) {
                messages.set("NeedMoreXp", "&7 You Need More XP Levels");
            }
            if (messages.getString("GiveEnchant") == null) {
                messages.set("GiveEnchant",
                        "&7 You have purchased a &c%Tier% &7enchant and recived a &c%Enchant% &7enchant!");
            }
            if (messages.getString("StartReload") == null) {
                messages.set("StartReload", "&7 Reloading Plugin...");
            }
            if (messages.getString("EndReload") == null) {
                messages.set("EndReload", "&7 Reload Complete took %ms%ms.");
            }
            if (messages.getString("NoPermission") == null) {
                messages.set("NoPermission", "&7 Insufficient Permission!");
            }
            if (messages.getString("EnableEnchant") == null) {
                messages.set("EnableEnchant", "&7 %Enchant% &7Enchant Enabled!");
            }
            if (messages.getString("DisableEnchant") == null) {
                messages.set("DisableEnchant", "&7 %Enchant% &7Enchant Disabled!");
            }
            if (messages.getString("UnknownEnchant") == null) {
                messages.set("UnknownEnchant", "&7 Unknown Enchant!");
            }
            if (messages.getString("UnknownPlayer") == null) {
                messages.set("UnknownPlayer", "&7 Unknown Player!");
            }
            if (messages.getString("EnchantDisabled") == null) {
                messages.set("EnchantDisabled", "&7 That Enchant Is Disabled!");
            }
            if (messages.getString("GivePlayerEnchant") == null) {
                messages.set("GivePlayerEnchant", "&7 You gave &c%Player%&7 a &c%Enchant% &7enchant!");
            }
            if (messages.getString("MustBePlayer") == null) {
                messages.set("MustBePlayer", "&7 You Bust be a player to do this command!");
            }
            if (messages.getString("ToMuchEnchants") == null) {
                messages.set("ToMuchEnchants", "&7 There Are to many enchants accosiated with this item!");
            }
            if (messages.getString("UnknownGkit") == null) {
                messages.set("UnknownGkit", "&7 The gkit %name% does not exist!");
            }
            if (messages.getString("GkitReset") == null) {
                messages.set("GkitReset", "&7 You have reset the %name% Gkit for %player%!");
            }
            if (messages.getString("GkitNoCooldown") == null) {
                messages.set("GkitNoCooldown", "&7 %player% has no cooldown for the %name% Gkit!");
            }
            if (messages.getString("Update") == null) {
                messages.set("Update", "&7 A new update was found! Version: %version%!");
            }
            if (messages.getString("NoItems") == null) {
                messages.set("NoItems", "&7 You have no items that can be enchanted with this crystal");
            }
            if (messages.getString("FlyEnabled") == null) {
                messages.set("FlyEnabled", "&7 Your flight has been &cEnabled!");
            }
            if (messages.getString("FlyDisabled") == null) {
                messages.set("FlyDisabled", "&7 Your flight has been &cDisabled!");
            }
            if (messages.getString("WaitMessage") == null) {
                messages.set("WaitMessage", "&7 You must wait &c%time% seconds!");
            }
            try {
                messages.save(msg);
            } catch (IOException ignored) {
            }
        }
        File file = new File("plugins//CustomEnchants//CustomEnchants.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                YamlConfiguration enchants = YamlConfiguration.loadConfiguration(file);
                ArrayList<String> hulk = new ArrayList<>();
                hulk.add("INCREASE_DAMAGE");
                hulk.add("JUMP");
                enchants.set("Enchantments.Hulk.Enabled", Boolean.TRUE);
                enchants.set("Enchantments.Hulk.MaxPower", 2);
                enchants.set("Enchantments.Hulk.StartingPower", 2);
                enchants.set("Enchantments.Hulk.Description", "Gives you speed strength and JumpBoost");
                enchants.set("Enchantments.Hulk.Options.PowerIncrease", 1);
                enchants.set("Enchantments.Hulk.Options.PotionEffects", hulk);
                enchants.set("Enchantments.Hulk.Options.ItemsEnchantable", "Armor");
                enchants.set("Enchantments.Hulk.Options.PotionTime", 9999);
                enchants.set("Enchantments.Hulk.Chance", 100);
                enchants.save(file);
            } catch (IOException ignored) {
            }
        }
    }

    @EventHandler
    public void onmove(@NotNull PlayerMoveEvent e) {
        if (Utils.hasenchant(getConfig().getString("Translate.Wings") + " I",
                e.getPlayer().getInventory().getBoots())) {
            File msg = new File("plugins//CustomEnchants//Messages.yml");
            YamlConfiguration messages = YamlConfiguration.loadConfiguration(msg);
            Player p = e.getPlayer();
            if (fly.contains(p.getName())) {
                if (!Support.inTerritory(p)) {
                    fly.remove(p.getName());
                    p.setAllowFlight(false);
                    p.setFlying(false);
                    p.sendMessage(Utils.prefix()
                            + ChatColor.translateAlternateColorCodes('&', messages.getString("FlyDisabled")));
                }

            } else {
                if (Support.inTerritory(p)) {
                    fly.add(p.getName());
                    p.setAllowFlight(true);
                    p.setFlying(true);
                    p.sendMessage(Utils.prefix()
                            + ChatColor.translateAlternateColorCodes('&', messages.getString("FlyEnabled")));
                }
            }
        }
    }

    public void addsnow(String player) {
        snow.add(player);
    }

    public void removensnow(String player) {
        if (snow.contains(player)) {
            snow.remove(player);
        }
    }

    public void removeblocktask(Location loc) {
        if(BlockTask.containsKey(loc)){
            BlockTask.remove(loc);
            BlockData.remove(loc);
        }
    }

    public boolean containsblocktask(Location loc) {
        return BlockTask.containsKey(loc);
    }

    private void JacketFix() {
        boolean disable = true;
        for (int counter = 1; counter <= getConfig().getInt("MaxPower.Jacket"); counter++) {
            if (getConfig().getBoolean("Enabled.Jacket" + Utils.convertPower(counter))) {
                disable = false;
            }
        }
        if (disable) {
            return;
        }
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (Bukkit.getServer().getOnlinePlayers() == null) {
                return;
            }
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                Double i = getConfig().getDouble("Options.DefaultHearts");
                if ((p.getInventory().getBoots() != null)
                        && (p.getInventory().getBoots().getItemMeta().getLore() != null)) {

                    for (int counter = 1; counter <= getConfig().getInt("MaxPower.Jacket"); counter++) {
                        if (Utils.hasenchant(
                                getConfig().getString("Translate.Jacket") + " " + Utils.convertPower(counter),
                                p.getInventory().getBoots())) {
                            i = i + counter;
                        }

                    }
                }

                if ((p.getInventory().getLeggings() != null)
                        && (p.getInventory().getLeggings().getItemMeta().getLore() != null)) {
                    for (int counter = 1; counter <= getConfig().getInt("MaxPower.Jacket"); counter++) {
                        if (Utils.hasenchant(
                                getConfig().getString("Translate.Jacket") + " " + Utils.convertPower(counter),
                                p.getInventory().getLeggings())) {
                            i = i + counter;
                        }
                    }
                }
                if ((p.getInventory().getChestplate() != null)
                        && (p.getInventory().getChestplate().getItemMeta().getLore() != null)) {
                    for (int counter = 1; counter <= getConfig().getInt("MaxPower.Jacket"); counter++) {
                        if (Utils.hasenchant(
                                getConfig().getString("Translate.Jacket") + " " + Utils.convertPower(counter),
                                p.getInventory().getChestplate())) {
                            i = i + counter;
                        }
                    }
                }
                if ((p.getInventory().getHelmet() != null)
                        && (p.getInventory().getHelmet().getItemMeta().getLore() != null)) {
                    for (int counter = 1; counter <= getConfig().getInt("MaxPower.Jacket"); counter++) {
                        if (Utils.hasenchant(
                                getConfig().getString("Translate.Jacket") + " " + Utils.convertPower(counter),
                                p.getInventory().getHelmet())) {
                            i = i + counter;
                        }
                    }
                }
                if (p.getMaxHealth() != i) {
                    p.setMaxHealth(i);
                }
            }
        }, 0L, 200L);
    }

    private void registerTarget() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (!Target.isEmpty()) {
                for (Projectile arrow : Target.keySet()) {
                    if (arrow.isDead() || arrow.isOnGround()) {
                        Target.remove(arrow);
                    } else {

                        if (Version.getVersion().equals(Version.v1_8_R1)) {
                            NMS_v1_8_R1.sendParticle(EnumParticle.REDSTONE,
                                    arrow.getLocation(), 0.0F, 0.0F, 0.0F, 1.0F, 10);
                        }
                        if (Version.getVersion().equals(Version.v1_8_R2)) {
                            NMS_v1_8_R2.sendParticle(net.minecraft.server.v1_8_R2.EnumParticle.REDSTONE,
                                    arrow.getLocation(), 0.0F, 0.0F, 0.0F, 1.0F, 10);
                        }
                        if (Version.getVersion().equals(Version.v1_8_R3)) {
                            NMS_v1_8_R3.sendParticle(net.minecraft.server.v1_8_R3.EnumParticle.REDSTONE,
                                    arrow.getLocation(), 0.0F, 0.0F, 0.0F, 1.0F, 10);
                        }
                        if (Version.getVersion().equals(Version.v1_9_R1)) {
                            NMS_v1_9_R1.sendParticle(net.minecraft.server.v1_9_R1.EnumParticle.REDSTONE,
                                    arrow.getLocation(), 0.0F, 0.0F, 0.0F, 1.0F, 10);
                        }
                        if (Version.getVersion().equals(Version.v1_9_R2)) {
                            NMS_v1_9_R2.sendParticle(net.minecraft.server.v1_9_R2.EnumParticle.REDSTONE,
                                    arrow.getLocation(), 0.0F, 0.0F, 0.0F, 1.0F, 10);
                        }
                        if (Version.getVersion().equals(Version.v1_10_R1)) {
                            NMS_v1_10_R1.sendParticle(net.minecraft.server.v1_10_R1.EnumParticle.REDSTONE,
                                    arrow.getLocation(), 0.0F, 0.0F, 0.0F, 1.0F, 10);
                        }
                        if (Version.getVersion().equals(Version.v1_11_R1)) {
                            NMS_v1_11_R1.sendParticle(net.minecraft.server.v1_11_R1.EnumParticle.REDSTONE,
                                    arrow.getLocation(), 0.0F, 0.0F, 0.0F, 1.0F, 10);
                        }
                    }
                }
            }
        }, 0L, 5L);
    }

    private void registerBow() {

        if (!getConfig().getBoolean("Enabled.FlareI")) {
            return;
        }
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (!Flare.isEmpty()) {
                for (Projectile arrow : Flare) {
                    if (arrow.isDead() || arrow.isOnGround()) {
                        Flare.remove(arrow);
                        arrow.getWorld().createExplosion(arrow.getLocation().getX(), arrow.getLocation().getY(),
                                arrow.getLocation().getZ(), 5.0F, false, false);
                    } else {

                        if (Version.getVersion().equals(Version.v1_8_R1)) {
                            NMS_v1_8_R1.sendParticle(net.minecraft.server.v1_8_R1.EnumParticle.FLAME,
                                    arrow.getLocation(), 0.0F, 0.0F, 0.0F, 1.0F, 10);
                        }
                        if (Version.getVersion().equals(Version.v1_8_R2)) {
                            NMS_v1_8_R2.sendParticle(net.minecraft.server.v1_8_R2.EnumParticle.FLAME,
                                    arrow.getLocation(), 0.0F, 0.0F, 0.0F, 1.0F, 10);
                        }
                        if (Version.getVersion().equals(Version.v1_8_R3)) {
                            NMS_v1_8_R3.sendParticle(net.minecraft.server.v1_8_R3.EnumParticle.FLAME,
                                    arrow.getLocation(), 0.0F, 0.0F, 0.0F, 1.0F, 10);
                        }
                        if (Version.getVersion().equals(Version.v1_9_R1)) {
                            NMS_v1_9_R1.sendParticle(net.minecraft.server.v1_9_R1.EnumParticle.FLAME,
                                    arrow.getLocation(), 0.0F, 0.0F, 0.0F, 1.0F, 10);
                        }
                        if (Version.getVersion().equals(Version.v1_9_R2)) {
                            NMS_v1_9_R2.sendParticle(net.minecraft.server.v1_9_R2.EnumParticle.FLAME,
                                    arrow.getLocation(), 0.0F, 0.0F, 0.0F, 1.0F, 10);
                        }
                        if (Version.getVersion().equals(Version.v1_10_R1)) {
                            NMS_v1_10_R1.sendParticle(net.minecraft.server.v1_10_R1.EnumParticle.FLAME,
                                    arrow.getLocation(), 0.0F, 0.0F, 0.0F, 1.0F, 10);
                        }
                        if (Version.getVersion().equals(Version.v1_11_R1)) {
                            NMS_v1_11_R1.sendParticle(net.minecraft.server.v1_11_R1.EnumParticle.FLAME,
                                    arrow.getLocation(), 0.0F, 0.0F, 0.0F, 1.0F, 10);
                        }
                    }
                }
            }
        }, 0L, 5L);
    }

    private void registerSnow() {
        if (!getConfig().getBoolean("Enabled.FrostyI")) {
            return;
        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (String player : snow) {
                Player p = Bukkit.getPlayer(player);
                if (p != null) {
                    if (p.getInventory().getBoots() != null) {
                        if (p.getInventory().getBoots().getItemMeta() != null) {
                            if (p.getInventory().getBoots().getItemMeta().getLore() != null) {
                                if (Utils.hasenchant(getConfig().getString("Translate.Frosty") + " I",
                                        p.getInventory().getBoots())) {

                                    if (Version.getVersion().equals(Version.v1_8_R1)) {
                                        NMS_v1_8_R1.sendParticle(
                                                net.minecraft.server.v1_8_R1.EnumParticle.SNOW_SHOVEL,
                                                p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                                                10);
                                    }
                                    if (Version.getVersion().equals(Version.v1_8_R2)) {
                                        NMS_v1_8_R2.sendParticle(
                                                net.minecraft.server.v1_8_R2.EnumParticle.SNOW_SHOVEL,
                                                p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                                                10);
                                    }
                                    if (Version.getVersion().equals(Version.v1_8_R3)) {
                                        NMS_v1_8_R3.sendParticle(
                                                net.minecraft.server.v1_8_R3.EnumParticle.SNOW_SHOVEL,
                                                p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                                                10);
                                    }
                                    if (Version.getVersion().equals(Version.v1_9_R1)) {
                                        NMS_v1_9_R1.sendParticle(
                                                net.minecraft.server.v1_9_R1.EnumParticle.SNOW_SHOVEL,
                                                p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                                                10);
                                    }
                                    if (Version.getVersion().equals(Version.v1_9_R2)) {
                                        NMS_v1_9_R2.sendParticle(
                                                net.minecraft.server.v1_9_R2.EnumParticle.SNOW_SHOVEL,
                                                p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                                                10);
                                    }
                                    if (Version.getVersion().equals(Version.v1_10_R1)) {
                                        NMS_v1_10_R1.sendParticle(
                                                net.minecraft.server.v1_10_R1.EnumParticle.SNOW_SHOVEL,
                                                p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                                                10);
                                    }
                                    if (Version.getVersion().equals(Version.v1_11_R1)) {
                                        NMS_v1_11_R1.sendParticle(
                                                net.minecraft.server.v1_11_R1.EnumParticle.SNOW_SHOVEL,
                                                p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                                                10);
                                    }
                                    Location loc = p.getLocation().add(0.0D, -1.0D, 0.0D);
                                    List<Block> blocks = getSquare(loc, p);
                                    for (final Block block : blocks)
                                        if ((block.getType() != Material.AIR) && (block.getType() != Material.SNOW)
                                                && (block.getType() != Material.ICE) && (block.getType() != null)) {
                                            Location l = block.getLocation().clone().add(0.0D, 1.0D, 0.0D);
                                            if (block.isLiquid()) {
                                                if ((block.getType() != Material.LAVA)
                                                        && (block.getType() != Material.STATIONARY_LAVA
                                                        && Support.allowsBreak(block.getLocation(), p)
                                                        && Support.canBreakBlock(p, block))) {
                                                    addblock(Material.PACKED_ICE, block.getType(), block.getLocation(), 20, true);
                                                }
                                            } else {
                                                Location a = l.clone().add(0.0D, -1.0D, 0.0D);
                                                if (a.getBlock().getType() != Material.SNOW
                                                        && a.getBlock().getType() != Material.WATER
                                                        && a.getBlock().getType() != Material.ICE
                                                        && a.getBlock().getType() != Material.PACKED_ICE
                                                        && a.getBlock().getType() != Material.YELLOW_FLOWER
                                                        && a.getBlock().getType() != Material.DEAD_BUSH
                                                        && a.getBlock().getType() != Material.LONG_GRASS
                                                        && Support.allowsBreak(a, p)
                                                        && Support.canBreakBlock(p, a.getBlock())) {
                                                    addblock(Material.SNOW, l.getBlock().getType(), l, 20, true);
                                                }

                                            }
                                        }

                                } else {
                                    snow.remove(player);
                                }
                            }
                        }
                    }
                }
            }
        }, 0L, 5L);
    }

    @NotNull
    private List<Block> getSquare(@NotNull Location loc, Player p) {
        List<Block> blocks = com.google.common.collect.Lists.newArrayList();
        for (int x = 2 * -1; x <= 2; x++) {
            for (int y = 2 * -1; y <= 2; y++) {
                for (int z = 2 * -1; z <= 2; z++) {
                    Block b = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                    if (Support.canBreakBlock(p, b)) {
                        Location location = b.getLocation().add(0.0D, 1.0D, 0.0D);
                        if ((location.getBlock().getType().equals(Material.AIR))
                                && (!b.getType().equals(Material.AIR))) {
                            blocks.add(b);
                        }
                    }
                }
            }
        }
        return blocks;
    }

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if ((p.getInventory().getBoots() != null) && (p.getInventory().getBoots().getItemMeta() != null)
                && (p.getInventory().getBoots().getItemMeta().getLore() != null)
                && (Utils.hasenchant(getConfig().getString("Translate.Frosty") + "I", p.getInventory().getBoots()))) {
            addsnow(p.getName());
        }
    }

    private void Inventory() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (!FuryTime.isEmpty()) {
                for (String player : FuryTime.keySet()) {
                    if (FuryTime.get(player) == 1) {
                        FuryTime.remove(player);
                        FuryData.remove(player);
                    } else {
                        FuryTime.put(player, FuryTime.get(player) - 1);
                    }
                }

            }
            if (Bukkit.getOnlinePlayers() == null)
                return;
            for (Player p : Bukkit.getOnlinePlayers()) {
                for (int i = 0; i <= p.getOpenInventory().getTopInventory().getSize() - 1; i++) {

                    if (p.getOpenInventory().getTopInventory() != null) {
                        if (p.getOpenInventory().getTopInventory().getItem(i) != null) {
                            if (p.getOpenInventory().getTitle().equals(Utils.getTitle())) {
                                if (p.getOpenInventory().getTopInventory() != null) {
                                    if (p.getOpenInventory().getTopInventory().getItem(i).getData() != null) {
                                        if (p.getOpenInventory().getTopInventory().getItem(i).getData().toString()
                                                .equals(Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 9, " ")
                                                        .getData().toString())) {
                                            p.getOpenInventory().getTopInventory().setItem(i,
                                                    Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 10, " "));

                                        } else if (p.getOpenInventory().getTopInventory().getItem(i).getData()
                                                .toString()
                                                .equals(Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 10, " ")
                                                        .getData().toString())) {
                                            p.getOpenInventory().getTopInventory().setItem(i,
                                                    Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 9, " "));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }

        }, 0L, 10L);
    }

    private void Inventory1() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (Bukkit.getOnlinePlayers() == null)
                return;
            for (Player p : Bukkit.getOnlinePlayers()) {
                for (int i = 0; i <= p.getOpenInventory().getTopInventory().getSize() - 1; i++) {
                    if (p.getOpenInventory().getTopInventory() != null) {
                        if (p.getOpenInventory().getTopInventory().getItem(i) != null) {
                            if (p.getOpenInventory().getTitle().equals(Utils.getTitle())) {
                                if (p.getOpenInventory().getTopInventory() != null) {
                                    if (p.getOpenInventory().getTopInventory().getItem(i).getData() != null) {
                                        if (p.getOpenInventory().getTopInventory().getItem(i).getData().toString()
                                                .equals(Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 3, " ")
                                                        .getData().toString())) {
                                            p.getOpenInventory().getTopInventory().setItem(i,
                                                    Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 11, " "));

                                        } else if (p.getOpenInventory().getTopInventory().getItem(i).getData()
                                                .toString()
                                                .equals(Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 11, " ")
                                                        .getData().toString())) {
                                            p.getOpenInventory().getTopInventory().setItem(i,
                                                    Utils.makeItem(Material.STAINED_GLASS_PANE, 1, 3, " "));
                                        }

                                    }
                                }

                            }
                        }

                    }
                }

            }

        }, 0L, 3L);
    }

    private void GkitsCooldown() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (String name : Gkits.keySet()) {
                if (Gkits.get(name) == 1) {
                    Gkits.remove(name);
                }else{
                    Gkits.put(name, Gkits.get(name) - 1);
                }
            }
//            for(String name : Abilities.keySet()){
//                if (Abilities.get(name) == 1) {
//                    Abilities.remove(name);
//                }
//                Abilities.put(name, Abilities.get(name) - 1);
//            }
        }, 0L, 20L);
    }

    private void Stealth() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for(String name : Stealth.keySet()){
                if (Stealth.get(name) == 1) {
                    Player player = Bukkit.getPlayer(name);
                    if(player != null){
                        for(Player p : Bukkit.getOnlinePlayers()){
                            p.showPlayer(Bukkit.getPlayer(name));
                        }
                    }
                    Stealth.remove(name);
                }else{

                    Stealth.put(name, Stealth.get(name) - 1);
                }
            }
        }, 0L, 20L);
    }

    private void registerHaste() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (!Bukkit.getOnlinePlayers().isEmpty()) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p != null) {
                        if (haste.containsKey(p.getUniqueId())) {
                            p.removePotionEffect(haste.get(p.getUniqueId()));
                            haste.remove(p.getUniqueId());
                        }
                        if ((p.getItemInHand() != null) && (p.getItemInHand().hasItemMeta())
                                && (p.getItemInHand().getItemMeta().hasLore())) {
                            for (int counter = 1; counter <= getConfig().getInt("MaxPower.Haste"); counter++) {
                                if (Utils.hasenchant(getConfig().getString("Translate.Haste") + " "
                                        + Utils.convertPower(counter), p.getItemInHand())) {
                                    haste.put(p.getUniqueId(), PotionEffectType.FAST_DIGGING);
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999,
                                            counter - 1, false));
                                }
                            }
                            for (int counter = 1; counter <= getConfig()
                                    .getInt("MaxPower.Assassinate"); counter++) {
                                if (Utils.hasenchant(getConfig().getString("Translate.Assassinate") + " "
                                        + Utils.convertPower(counter), p.getItemInHand())) {
                                    haste.put(p.getUniqueId(), PotionEffectType.FAST_DIGGING);
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999,
                                            counter - 1, false));
                                }
                            }

                        }
                    }
                }

            }
        }, 0L, 10L);
    }
}
