/*
 * CommandStands Class
 * Main class of the CommandStands plugin.
 */

package net.orendigo.commandstands;

import java.io.File;
import java.io.IOException;

import net.orendigo.commandstands.command.CommandManager;
import net.orendigo.commandstands.externalmanagers.WorldGuardManager;
import net.orendigo.commandstands.event.EventManager;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author Orendigo
 */
public class CommandStands extends JavaPlugin implements Listener {
    
    private static CommandStands instance;
    
    // messages.yml config file
    private File messagesFile;
    private FileConfiguration messagesConfig;
    
    public CommandManager commandManager;
    
    // managers for external plugins
    public WorldGuardManager worldGuardManager;
    
    public void onLoad() {
        setInstance(this);
        loadWorldGuard();
    }
    
    // attempting to load WorldGuard / WorldEdit
    private void loadWorldGuard() {
        try {
            Class.forName("com.sk89q.worldguard.WorldGuard");
            Class.forName("com.sk89q.worldedit.WorldEdit");
            worldGuardManager = new WorldGuardManager();
            worldGuardManager.setup();
            getLogger().info("Found WorldGuard && WorldEdit! Stands are now protected in regions!");
        } catch (ClassNotFoundException e) {
            getLogger().info("Missing either WorldGuard or WorldEdit! Stands have no protection!");
        }

        // in the future I may add custom worldguard flags (?)
        // at the moment the plugin only cares if the player can build in the region
        //if (worldGuardManager != null) worldGuardManager.register();
    }
    
    public void onEnable() {
        createMessagesConfig();
        Bukkit.getPluginManager().registerEvents(new EventManager(), this);
        (this.commandManager = new CommandManager()).setup();
        System.out.println("CommandStands loaded successfully!");
    }
    
    public void onDisable() {
        System.out.println("CommandStands Disabled.");
    }
    
    public static CommandStands getInstance() {
        return CommandStands.instance;
    }

    private void setInstance(final CommandStands instance) {
        CommandStands.instance = instance;
    }
    
    public WorldGuardManager getWorldGuardManager() {
        return this.worldGuardManager;
    }
    
    public boolean hasWorldGuard() {
        return worldGuardManager != null;
    }
    
    public void log(String info) {
        getLogger().info(info);
    }
    
    public void createMessagesConfig() {
        messagesFile = new File(getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            saveResource("messages.yml", false);
         }

        messagesConfig = new YamlConfiguration();
        try {messagesConfig.load(messagesFile);}
        catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    
    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }
    
    public File getMessagesFile() {
        return messagesFile;
    }
}
