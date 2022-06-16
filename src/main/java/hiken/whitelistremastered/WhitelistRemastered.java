package hiken.whitelistremastered;

import hiken.whitelistremastered.commands.WhitelistCommand;
import hiken.whitelistremastered.events.PlayerJoin;
import hiken.whitelistremastered.utils.DatabaseManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class WhitelistRemastered extends JavaPlugin {

    @Getter private static WhitelistRemastered instance;
    @Getter DatabaseManager databaseManager;

    PluginDescriptionFile pluginDescriptionFile = getDescription();
    @Getter String prefix = "[" + pluginDescriptionFile.getName() + " v" + pluginDescriptionFile.getVersion() + "] ";

    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        databaseManager = new DatabaseManager();
        if(getConfig().getString("storage.password").equals("whitelist_123")) {
            error("Please change the database password. Plugin is now disabled");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        databaseManager.setupConnection();
        this.getCommand("wr").setExecutor(new WhitelistCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
    }

    public void onDisable() {
        instance = null;
    }

    public String color(String message) {
        return message.replaceAll("&", "§");
    }

    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage(this.getPrefix() + color("&a" + message));
    }

    public void error(String message) {
        Bukkit.getConsoleSender().sendMessage(this.getPrefix() + color("&c" + message));
    }

}
