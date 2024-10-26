package io.github.kurrycat.mpknetapi.bukkit.listener;

import io.github.kurrycat.mpknetapi.bukkit.MPKApiPlugin;
import io.github.kurrycat.mpknetapi.bukkit.network.MPKPacketManager;
import io.github.kurrycat.mpknetapi.common.MPKNetworking;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;

import java.util.UUID;

@SuppressWarnings("unused")
public class MPKPlayerListener implements Listener {
    private static final MPKNetworking net = MPKNetworking.INSTANCE;
    private static final MPKApiPlugin plugin = MPKApiPlugin.getInstance();

    @EventHandler
    public void onRegister(PlayerRegisterChannelEvent event) {
        if (!event.getChannel().equals(MPKNetworking.MESSENGER_CHANNEL)) {
            return;
        }

        UUID uuid = event.getPlayer().getUniqueId();
        if (net.getPacketQueue().containsKey(uuid)) {
            net.getPacketQueue().get(uuid).forEach(packet -> MPKPacketManager.sendPacket(event.getPlayer(), packet));
        }
    }

    @EventHandler
    public void onUnregister(PlayerUnregisterChannelEvent event) {
        if (!event.getChannel().equals(MPKNetworking.MESSENGER_CHANNEL)) {
            return;
        }

        plugin.getMpkPlayers().remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.getMpkPlayers().remove(event.getPlayer().getUniqueId());
    }
}
