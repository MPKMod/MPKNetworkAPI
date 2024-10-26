package io.github.kurrycat.mpknetapi.common;

import io.github.kurrycat.mpknetapi.common.network.packet.MPKPacket;

import java.util.*;

public enum MPKNetworking {
    INSTANCE;

    public static final String CHANNEL_NAMESPACE = "mpkmod2";
    public static final String CHANNEL_PATH = "network";
    public static final String MESSENGER_CHANNEL = CHANNEL_NAMESPACE + ":" + CHANNEL_PATH;
    private final Map<UUID, List<MPKPacket>> packetQueue = new HashMap<>();

    public Map<UUID, List<MPKPacket>> getPacketQueue() {
        return packetQueue;
    }
}
