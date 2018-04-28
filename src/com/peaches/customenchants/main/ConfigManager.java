package com.peaches.customenchants.main;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class ConfigManager {

    private static final ConfigManager instance = new ConfigManager();

    @NotNull
    public static ConfigManager getInstance() {
        return instance;
    }

    private Plugin p;

    private FileConfiguration tinker;
    private File tfile;
    FileConfiguration dconfig;

    public void setup(@NotNull Plugin p) {
        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }

        this.tfile = new File(p.getDataFolder(), "Tinker.yml");
        if (!this.tfile.exists()) {
            try {
                File en = new File(p.getDataFolder(), "/Tinker.yml");
                InputStream E = getClass().getResourceAsStream("/Tinker.yml");

                saveResource(E, en);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.tinker = YamlConfiguration.loadConfiguration(this.tfile);
    }

    public FileConfiguration getTinker() {
        return this.tinker;
    }

    public void reloadTinker() {
        this.tinker = YamlConfiguration.loadConfiguration(this.tfile);
    }

    public PluginDescriptionFile getDesc() {
        return this.p.getDescription();
    }

    private static void saveResource(InputStream in, @NotNull File out)
            throws Exception
    {
        try (FileOutputStream fos = new FileOutputStream(out)) {
            byte[] buf = new byte['?'];
            int i;
            while ((i = in.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } finally {
            if (in != null) {
                in.close();
            }

        }
    }
}
