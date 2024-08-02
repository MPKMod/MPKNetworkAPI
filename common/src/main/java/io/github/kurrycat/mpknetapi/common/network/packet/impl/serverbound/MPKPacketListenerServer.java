package io.github.kurrycat.mpknetapi.common.network.packet.impl.serverbound;

import io.github.kurrycat.mpknetapi.common.network.packet.impl.MPKPacketListener;

/**
 * Packet listener interface for the server.
 */
public interface MPKPacketListenerServer extends MPKPacketListener {
    void handleRegister(MPKPacketRegister packet);
    void handleModuleUpdate(MPKPacketModuleUpdate packet);
}
