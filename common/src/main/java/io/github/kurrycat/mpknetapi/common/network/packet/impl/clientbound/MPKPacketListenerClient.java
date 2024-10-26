package io.github.kurrycat.mpknetapi.common.network.packet.impl.clientbound;

import io.github.kurrycat.mpknetapi.common.network.packet.impl.MPKPacketListener;

/**
 * Packet listener interface for the client.
 */
public interface MPKPacketListenerClient extends MPKPacketListener {
    void handleDisableModules(MPKPacketDisableModules packet);
}
