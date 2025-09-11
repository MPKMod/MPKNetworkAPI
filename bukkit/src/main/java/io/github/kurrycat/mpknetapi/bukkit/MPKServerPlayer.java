package io.github.kurrycat.mpknetapi.bukkit;

import io.github.kurrycat.mpknetapi.bukkit.network.MPKPacketManager;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.clientbound.MPKPacketDisableModules;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.clientbound.MPKPacketSetLandingBlock;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MPKServerPlayer {
    private final static ModuleConfig config = MPKApiPlugin.getInstance().getModuleConfig();

    private final UUID uuid;
    private final List<String> registeredModules;
    private List<String> updatedModules;
    private boolean updated;

    public MPKServerPlayer(Player player, List<String> registeredModules) {
        this.uuid = player.getUniqueId();
        this.registeredModules = registeredModules;

        List<String> disallowedModules = getNonCompliantModules();
        if (disallowedModules.isEmpty()) return;

        MPKPacketManager.sendPacket(player, new MPKPacketDisableModules(disallowedModules), true);

        if (!(config.isFailKick() || config.isFailNotify())) return;

        Bukkit.getScheduler().runTaskLater(MPKApiPlugin.getInstance(), () -> {
            if (!player.isOnline() || player.hasPermission("mpknetapi.bukkit.fail.bypass") || updated) return;

            MPKApiPlugin.LOGGER.warning("Player " + player.getName() + " didn't comply with the MPK module configuration!");

            if (config.isFailNotify()) {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if (!p.hasPermission("mpknetapi.bukkit.notify")) return;

                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            config.getFailNotifyMessage()
                                    .replace("%player%", Bukkit.getPlayer(uuid).getName())
                                    .replace("%modules%", String.join(", ", getNonCompliantModules()))
                    ));
                });
            }

            if (config.isFailKick()) {
                Bukkit.getPlayer(uuid).kickPlayer(ChatColor.translateAlternateColorCodes('&',
                        config.getFailKickMessage()
                                .replace("%modules%", String.join(", ", getNonCompliantModules()))
                ));
            }
        }, config.getFailDelay());
    }

    public UUID getUuid() {
        return uuid;
    }

    public void updateModules(List<String> modules) {
        updated = true;
        updatedModules = modules;

        if (!(config.isFailKick() || config.isFailNotify())) return;

        Player player = Bukkit.getPlayer(uuid);
        if (!config.checkModuleCompliance(modules) && !player.hasPermission("mpknetapi.bukkit.fail.bypass")) {
            MPKApiPlugin.LOGGER.warning("Player " + player.getName() + " didn't comply with the MPK module configuration!");

            if (config.isFailNotify()) {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if (!p.hasPermission("mpknetapi.bukkit.notify")) return;

                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        config.getFailNotifyMessage()
                            .replace("%player%", player.getName())
                            .replace("%modules%", String.join(", ", getNonCompliantModules()))
                    ));
                });
            }

            if (config.isFailKick()) {
                Bukkit.getPlayer(uuid).kickPlayer(ChatColor.translateAlternateColorCodes('&',
                    config.getFailKickMessage().replace("%modules%", String.join(", ", getNonCompliantModules())
                )));
            }
        }
    }

    public List<String> getModules() {
        return updated ? updatedModules : registeredModules;
    }

    public List<String> getDisabledModules() {
        List<String> disabledModules = new ArrayList<>();
        for (String module : registeredModules) {
            if (!getModules().contains(module)) {
                disabledModules.add(module);
            }
        }

        return disabledModules;
    }

    public List<String> getNonCompliantModules() {
        return config.getIncompliantModules(getModules());
    }

    public void sendLandingBlock(int x, int y, int z) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null && player.isOnline()) {
            MPKPacketManager.sendPacket(player, new MPKPacketSetLandingBlock(x, y, z));
        }
    }
}
