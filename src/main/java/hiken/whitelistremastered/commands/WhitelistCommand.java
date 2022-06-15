package hiken.whitelistremastered.commands;

import hiken.whitelistremastered.WhitelistRemastered;
import hiken.whitelistremastered.utils.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WhitelistCommand implements CommandExecutor {

    WhitelistRemastered instance = WhitelistRemastered.getInstance();

    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if(cs instanceof ConsoleCommandSender) {
            if(!instance.getConfig().getBoolean("settings.console-enabled")) {
                cs.sendMessage(instance.color(instance.getConfig().getString("messages.no-console")));
                return true;
            }
            if(args.length == 0) {
                cs.sendMessage(instance.color(instance.getConfig().getString("messages.command-usage")));
                return true;
            }
            String subCommand = args[0];
            if(subCommand.equalsIgnoreCase("add")) {
                if(args[1] == null) {
                    cs.sendMessage(instance.color(instance.getConfig().getString("messages.subcommands.add.usage")));
                    return true;
                }
                if(instance.getDatabaseManager().isPlayerInWhitelist(args[1])) {
                    cs.sendMessage(instance.color(
                            instance.getConfig().getString("messages.subcommands.add.player-already-in-whitelist")
                                    .replace("{playerName}", args[1])
                    ));
                    return true;
                }
                instance.getDatabaseManager().addPlayerToWhitelist(args[1]);
                cs.sendMessage(instance.color(
                        instance.getConfig().getString("messages.subcommands.add.player-added")
                                .replace("{playerName}", args[1])
                ));
                return true;
            }
            if(subCommand.equalsIgnoreCase("rem")) {
                if(args[1] == null) {
                    cs.sendMessage(instance.color(instance.getConfig().getString("messages.subcommands.rem.usage")));
                    return true;
                }
                if(!instance.getDatabaseManager().isPlayerInWhitelist(args[1])) {
                    cs.sendMessage(instance.color(
                            instance.getConfig().getString("messages.subcommands.rem.player-not-in-whitelist")
                                    .replace("{playerName}", args[1])
                    ));
                    return true;
                }
                instance.getDatabaseManager().remPlayerFromWhitelist(args[1]);
                cs.sendMessage(instance.color(
                        instance.getConfig().getString("messages.subcommands.rem.player-removed")
                                .replace("{playerName}", args[1])
                ));
                return true;
            }
            if(subCommand.equalsIgnoreCase("toggle")) {
                if(instance.getConfig().getBoolean("settings.whitelist-on")) {
                    cs.sendMessage(
                            instance.color(
                                    instance.getConfig().getString("messages.subcommands.toggle.turned-off")
                            )
                    );
                    instance.getConfig().set("settings.whitelist-on", false);
                }else {
                    cs.sendMessage(
                            instance.color(
                                    instance.getConfig().getString("messages.subcommands.toggle.turned-on")
                            )
                    );
                    instance.getConfig().set("settings.whitelist-on", true);
                }
                instance.saveConfig();
                instance.reloadConfig();
                return true;
            }
            if(subCommand.equalsIgnoreCase("status")) {
                boolean wStauts = instance.getConfig().getBoolean("settings.whitelist-on");
                if(wStauts) {
                    cs.sendMessage(instance.color(
                            instance.getConfig().getString("messages.subcommands.status.whitelist-on")
                    ));
                }else {
                    cs.sendMessage(instance.color(
                            instance.getConfig().getString("messages.subcommands.status.whitelist-off")
                    ));
                }
                return true;
            }
            if(subCommand.equalsIgnoreCase("list")) {
                List<String> whitelistedUsers = new ArrayList<>();
                for(User user : instance.getDatabaseManager().getWhitelistedUsers()) {
                    whitelistedUsers.add(user.getName());
                }
                if(whitelistedUsers.size() == 0) {
                    cs.sendMessage(
                            instance.color(
                                    instance.getConfig().getString("messages.subcommands.list.no-players")
                            )
                    );
                    return true;
                }
                cs.sendMessage(
                        instance.color(
                                instance.getConfig().getString("messages.subcommands.list.allowed-players")
                                        .replace("{totalUsers}", String.valueOf(whitelistedUsers.size()))
                                        .replace("{usersList}", String.join(" ", whitelistedUsers))
                        )
                );
                return true;
            }
            if(subCommand.equalsIgnoreCase("reload")) {
                cs.sendMessage(
                        instance.color(
                                instance.getConfig().getString("messages.config-reloaded")
                        )
                );
                instance.reloadConfig();
                return true;
            }
        }
        Player player = (Player) cs;
        if(!player.hasPermission("whitelistremastered.admin")) {
            player.sendMessage(
                    instance.color(
                            instance.getConfig().getString("messages.no-perms")
                    )
            );
            return true;
        }
        if(args.length == 0) {
            player.sendMessage(instance.color(instance.getConfig().getString("messages.command-usage")));
            return true;
        }
        String subCommand = args[0];
        if(subCommand.equalsIgnoreCase("add")) {
            if(args[1] == null) {
                player.sendMessage(instance.color(instance.getConfig().getString("messages.subcommands.add.usage")));
                return true;
            }
            if(instance.getDatabaseManager().isPlayerInWhitelist(args[1])) {
                player.sendMessage(instance.color(
                        instance.getConfig().getString("messages.subcommands.add.player-already-in-whitelist")
                                .replace("{playerName}", args[1])
                ));
                return true;
            }
            instance.getDatabaseManager().addPlayerToWhitelist(args[1]);
            player.sendMessage(instance.color(
                    instance.getConfig().getString("messages.subcommands.add.player-added")
                            .replace("{playerName}", args[1])
            ));
            return true;
        }
        if(subCommand.equalsIgnoreCase("rem")) {
            if(args[1] == null) {
                player.sendMessage(instance.color(instance.getConfig().getString("messages.subcommands.rem.usage")));
                return true;
            }
            if(!instance.getDatabaseManager().isPlayerInWhitelist(args[1])) {
                player.sendMessage(instance.color(
                        instance.getConfig().getString("messages.subcommands.rem.player-not-in-whitelist")
                                .replace("{playerName}", args[1])
                ));
                return true;
            }
            instance.getDatabaseManager().remPlayerFromWhitelist(args[1]);
            player.sendMessage(instance.color(
                    instance.getConfig().getString("messages.subcommands.rem.player-removed")
                            .replace("{playerName}", args[1])
            ));
        }
        if(subCommand.equalsIgnoreCase("toggle")) {
            if(instance.getConfig().getBoolean("settings.whitelist-on")) {
                player.sendMessage(
                        instance.color(
                                instance.getConfig().getString("messages.subcommands.toggle.turned-off")
                        )
                );
                instance.getConfig().set("settings.whitelist-on", false);
            }else {
                player.sendMessage(
                        instance.color(
                                instance.getConfig().getString("messages.subcommands.toggle.turned-on")
                        )
                );
                instance.getConfig().set("settings.whitelist-on", true);
            }
            instance.saveConfig();
            instance.reloadConfig();
            return true;
        }
        if(subCommand.equalsIgnoreCase("status")) {
            boolean wStauts = instance.getConfig().getBoolean("settings.whitelist-on");
            if(wStauts) {
                player.sendMessage(instance.color(
                        instance.getConfig().getString("messages.subcommands.status.whitelist-on")
                ));
            }else {
                player.sendMessage(instance.color(
                        instance.getConfig().getString("messages.subcommands.status.whitelist-off")
                ));
            }
            return true;
        }
        if(subCommand.equalsIgnoreCase("list")) {
            List<String> whitelistedUsers = new ArrayList<>();
            for(User user : instance.getDatabaseManager().getWhitelistedUsers()) {
                whitelistedUsers.add(user.getName());
            }
            if(whitelistedUsers.size() == 0) {
                player.sendMessage(
                        instance.color(
                                instance.getConfig().getString("messages.subcommands.list.no-players")
                        )
                );
                return true;
            }
            player.sendMessage(
                    instance.color(
                            instance.getConfig().getString("messages.subcommands.list.allowed-players")
                                    .replace("{totalUsers}", String.valueOf(whitelistedUsers.size()))
                                    .replace("{usersList}", String.join(" ", whitelistedUsers))
                    )
            );
            return true;
        }
        if(subCommand.equalsIgnoreCase("reload")) {
            player.sendMessage(
                    instance.color(
                            instance.getConfig().getString("messages.config-reloaded")
                    )
            );
            instance.reloadConfig();
            return true;
        }
        return true;
    }

}
