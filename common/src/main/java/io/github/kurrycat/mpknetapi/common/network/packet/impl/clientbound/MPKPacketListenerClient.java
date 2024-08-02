package io.github.kurrycat.mpknetapi.common.network.packet.impl.clientbound;

import io.github.kurrycat.mpknetapi.common.network.packet.impl.MPKPacketListener;

/**
 * Packet listener interface for the client.
 */
public interface MPKPacketListenerClient extends MPKPacketListener {
    void handleModuleBlacklist(MPKPacketModuleBlacklist packet);
    void handleModuleWhitelist(MPKPacketModuleWhitelist packet);
}
