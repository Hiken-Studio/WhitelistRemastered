package hiken.whitelistremastered.events;

import hiken.whitelistremastered.WhitelistRemastered;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    WhitelistRemastered instance = WhitelistRemastered.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player joiner = e.getPlayer();
        if(instance.getConfig().getBoolean("settings.whitelist-on")) {
            if(!instance.getDatabaseManager().isPlayerInWhitelist(joiner.getName())) {
                joiner.kickPlayer(
                        instance.color(
                                instance.getConfig().getString("messages.kick-no-whitelisted-player")
                        )
                );
            }
        }
    }

}
