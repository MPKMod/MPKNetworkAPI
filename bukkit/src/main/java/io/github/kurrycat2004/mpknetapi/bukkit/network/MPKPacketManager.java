package io.github.kurrycat2004.mpknetapi.bukkit.network;

import io.github.kurrycat2004.mpknetapi.bukkit.MPKApiPlugin;
import io.github.kurrycat2004.mpknetapi.bukkit.network.event.MPKPacketSendEvent;
import io.github.kurrycat2004.mpknetapi.common.MPKNetworking;
import io.github.kurrycat2004.mpknetapi.common.network.packet.MPKPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@SuppressWarnings("all")
public class MPKPacketManager {
    private static final MPKNetworking net = MPKNetworking.INSTANCE;

    public static boolean sendPacket(Player player, MPKPacket packet) {
        if (!isMpkPlayer(player)) {
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
        return net.getMpkPlayers().containsKey(player.getUniqueId());
    }
}
