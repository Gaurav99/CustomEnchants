package com.peaches.customenchants.Support;

import be.maximvdw.mvdwupdater.MVdWUpdater;
import be.maximvdw.mvdwupdater.spigotsite.api.exceptions.ConnectionFailedException;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;

class Updater {
    public static void Update(@NotNull String path) {
        MVdWUpdater updater = (MVdWUpdater) Bukkit.getPluginManager().getPlugin("MVdWUpdater");
        try {
            if (updater.hasBought(updater.getSpigotUser(), 38733)) {
                File pluginFile = null;
                try {
                    pluginFile = new File(URLDecoder.decode(path,
                            "UTF-8"));
                } catch (UnsupportedEncodingException ignored) {

                }

                File outputFile = null;
                try {
                    outputFile = new File(Bukkit.getUpdateFolderFile(), Objects.requireNonNull(pluginFile).getName());
                } catch (Exception ignored) {

                }
                updater.getSpigotSiteAPI().getResourceManager()
                        .getResourceById(38733, updater.getSpigotUser()).downloadResource(updater.getSpigotUser(), outputFile);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rl");
            }
        } catch (ConnectionFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
