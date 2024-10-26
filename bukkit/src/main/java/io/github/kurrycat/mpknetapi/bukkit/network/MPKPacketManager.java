package io.github.kurrycat.mpknetapi.bukkit.network;

import io.github.kurrycat.mpknetapi.bukkit.MPKApiPlugin;
import io.github.kurrycat.mpknetapi.bukkit.network.event.MPKPacketSendEvent;
import io.github.kurrycat.mpknetapi.common.MPKNetworking;
import io.github.kurrycat.mpknetapi.common.network.packet.MPKPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@SuppressWarnings("all")
public class MPKPacketManager {
    private static final MPKApiPlugin plugin = MPKApiPlugin.getInstance();

    public static boolean sendPacket(Player player, MPKPacket packet) {
        return sendPacket(player, packet, false);
    }

    public static boolean sendPacket(Player player, MPKPacket packet, boolean force) {
        if (!force && !isMpkPlayer(player)) {
            return false;
        }

        MPKPacketSendEvent event = new MPKPacketSendEvent(player, packet);
        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            player.sendPluginMessage(MPKApiPlugin.getInstance(), MPKNetworking.MESSENGER_CHANNEL, packet.getData());
            return true;
        }

        return false;
    }

    public static boolean isMpkPlayer(Player player) {
        return plugin.getMpkPlayers().containsKey(player.getUniqueId());
    }
}
