package io.github.kurrycat.mpknetapi.bukkit.network.impl;

import io.github.kurrycat.mpknetapi.bukkit.event.MPKModuleMessageEvent;
import io.github.kurrycat.mpknetapi.bukkit.event.MPKPlayerRegisterEvent;
import io.github.kurrycat.mpknetapi.common.MPKNetworking;
import io.github.kurrycat.mpknetapi.common.MPKServerPlayer;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.serverbound.*;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.shared.*;
import org.bukkit.Bukkit;

import java.util.UUID;

public class MPKPacketListenerServerImpl implements MPKPacketListenerServer {
    private static final MPKNetworking net = MPKNetworking.INSTANCE;

    @Override
    public void handleRegister(MPKPacketRegister packet) {
        UUID uuid = (UUID) packet.getAttachment();

        MPKServerPlayer mpkPlayer = new MPKServerPlayer(uuid, packet.getLoadedModules());
        net.getMpkPlayers().put(uuid, mpkPlayer);
        Bukkit.getServer().getPluginManager().callEvent(new MPKPlayerRegisterEvent(mpkPlayer));
    }

    @Override
    public void handleModuleUpdate(MPKPacketModuleUpdate packet) {
        UUID uuid = (UUID) packet.getAttachment();

        if (net.getMpkPlayers().get(uuid) == null) return;

        MPKServerPlayer player = net.getMpkPlayers().get(uuid);
        player.loadedModules = packet.getNewModules();
    }

    @Override
    public void handleModuleMessage(MPKPacketModuleMessage packet) {
        UUID uuid = (UUID) packet.getAttachment();
        Bukkit.getServer().getPluginManager().callEvent(new MPKModuleMessageEvent(uuid, packet));
    }
}
