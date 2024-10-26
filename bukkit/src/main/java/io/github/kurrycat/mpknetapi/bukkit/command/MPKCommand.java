package io.github.kurrycat.mpknetapi.bukkit.command;

import io.github.kurrycat.mpknetapi.bukkit.MPKApiPlugin;
import io.github.kurrycat.mpknetapi.bukkit.network.MPKPacketManager;
import io.github.kurrycat.mpknetapi.bukkit.MPKServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MPKCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender.hasPermission("mpknetapi.bukkit.command") || sender.isOp())) {
            return false;
        }

        if (args.length < 1 || args[0].equalsIgnoreCase("help")) {
            this.sendColoredMessages(sender,
                    "&8--- &6MPK Server Help &8---",
                    "&aCommands:",
                    "&7  /mpk &ehelp &7- &6Help",
                    "&7  /mpk &elist &7- &6List all players using MPK",
                    "&7  /mpk &echeck &a<username> &7- &6Check if a player is using MPK (and list their enabled modules)"
            );

            return true;
        }

        Map<UUID, MPKServerPlayer> mpkPlayers = MPKApiPlugin.getInstance().getMpkPlayers();
        switch (args[0].toLowerCase()) {
            case "list": {
                sendColoredMessages(sender, "&cMPK &8> &7Players:");
                for (UUID uuid : mpkPlayers.keySet()) {
                    sendColoredMessages(sender, "&8 - &7" + Bukkit.getServer().getPlayer(uuid).getName());
                }
                break;
            }

            case "check": {
                if (args.length < 2) {
                    sendColoredMessages(sender, "&6MPK &8> &4Invalid argument count! Usage: /mpk check <username>");
                    break;
                }

                Player p = Bukkit.getPlayer(args[1]);
                if (p == null) {
                    sendColoredMessages(sender, "&6MPK &8> &4Player not found!");
                    break;
                }

                if (!MPKPacketManager.isMpkPlayer(p)) {
                    sendColoredMessages(sender, "&7" + args[1] + " &cisn't &7using MPK Mod!");
                    break;
                }

                MPKServerPlayer mpkPlayer = mpkPlayers.get(p.getUniqueId());
                List<String> loadedModules = mpkPlayer.getModules();
                sendColoredMessages(sender,
                        "&6MPK &8> &7" + args[1] + " &ais &7using MPK Mod!",
                        loadedModules.isEmpty() ? "&7No modules loaded." : "&7Modules loaded (&a" + loadedModules.size() + "&7):"
                );

                for (String module : loadedModules) {
                    String color = mpkPlayer.getNonCompliantModules().contains(module) ? "&c" : "&a";
                    sendColoredMessages(sender, "  &7 - " + color + module);
                }

                List<String> disabledModules = mpkPlayer.getDisabledModules();
                sendColoredMessages(sender,
                        disabledModules.isEmpty() ? "&7No disabled modules." : "&7Modules disabled (&8" + disabledModules.size() + "&7):"
                );

                for (String module : disabledModules) {
                    sendColoredMessages(sender, "  &7 - &8" + module);
                }

                break;
            }
        }

        return true;
    }

    private void sendColoredMessages(CommandSender sender, String ...msgs) {
        for (String msg : msgs) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        }
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> toReturn = new ArrayList<>();
        if (!(sender.hasPermission("mpknetapi.bukkit.command") || sender.isOp())) {
            return toReturn;
        }

        switch(args.length) {
            case 1:
                toReturn.add("help");
                toReturn.add("list");
                toReturn.add("check");
                break;

            case 2:
                if (!args[0].equalsIgnoreCase("check")) break;

                List<String> players = new ArrayList<>();
                Bukkit.getOnlinePlayers().forEach(p -> players.add(p.getName()));
                return players;
        }

        return toReturn;
    }
}
