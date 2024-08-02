package io.github.kurrycat.mpknetapi.bukkit.event;

import io.github.kurrycat.mpknetapi.common.network.packet.impl.shared.MPKPacketModuleMessage;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import java.util.UUID;

@SuppressWarnings("unused")
public class MPKModuleMessageEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final MPKPacketModuleMessage packet;

    public MPKModuleMessageEvent(UUID playerUuid, MPKPacketModuleMessage packet) {
        super(Bukkit.getPlayer(playerUuid));
        this.packet = packet;
    }

    public MPKPacketModuleMessage getPacket() {
        return packet;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}