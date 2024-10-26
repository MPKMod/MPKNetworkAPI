package io.github.kurrycat.mpknetapi.bukkit.network.impl;

import io.github.kurrycat.mpknetapi.bukkit.MPKApiPlugin;
import io.github.kurrycat.mpknetapi.bukkit.event.MPKModuleMessageEvent;
import io.github.kurrycat.mpknetapi.bukkit.event.MPKPlayerRegisterEvent;
import io.github.kurrycat.mpknetapi.bukkit.MPKServerPlayer;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.serverbound.*;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.shared.*;
import org.bukkit.Bukkit;

import java.util.UUID;

public class MPKPacketListenerServerImpl implements MPKPacketListenerServer {
    private static MPKApiPlugin plugin;

    @Override
    public void handleRegister(MPKPacketRegister packet) {
        UUID uuid = (UUID) packet.getAttachment();

        MPKServerPlayer mpkPlayer = new MPKServerPlayer(Bukkit.getPlayer(uuid), packet.getLoadedModules());
        plugin.getMpkPlayers().put(uuid, mpkPlayer);
        Bukkit.getServer().getPluginManager().callEvent(new MPKPlayerRegisterEvent(mpkPlayer));
    }

    @Override
    public void handleModuleUpdate(MPKPacketModuleUpdate packet) {
        UUID uuid = (UUID) packet.getAttachment();
        MPKServerPlayer player = plugin.getMpkPlayers().get(uuid);

        if (player == null) return;
        player.updateModules(packet.getNewModules());
    }

    @Override
    public void handleModuleMessage(MPKPacketModuleMessage packet) {
        UUID uuid = (UUID) packet.getAttachment();
        Bukkit.getServer().getPluginManager().callEvent(new MPKModuleMessageEvent(uuid, packet));
    }

    public static void setPlugin(MPKApiPlugin plugin) {
        MPKPacketListenerServerImpl.plugin = plugin;
    }
}
