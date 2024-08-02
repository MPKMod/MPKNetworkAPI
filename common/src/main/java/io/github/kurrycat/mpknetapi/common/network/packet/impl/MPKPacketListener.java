package io.github.kurrycat.mpknetapi.common.network.packet.impl;

import io.github.kurrycat.mpknetapi.common.network.packet.impl.shared.MPKPacketModuleMessage;

/**
 * Shared packet listener interface (server & client).
 */
public interface MPKPacketListener {
    void handleModuleMessage(MPKPacketModuleMessage packet);
}
