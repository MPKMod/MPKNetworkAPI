package io.github.kurrycat.mpknetapi.common.network.packet.impl.shared;

import io.github.kurrycat.mpknetapi.common.network.packet.MPKPacket;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.MPKPacketListener;
import io.github.kurrycat.mpknetapi.common.network.MPKByteBuf;

public class MPKPacketModuleMessage extends MPKPacket {
    private String moduleId;
    private int messageSize;
    private byte[] message;

    public MPKPacketModuleMessage() {}

    @Override
    public void read(MPKByteBuf buf) {
        this.moduleId = buf.readString();
        this.messageSize = buf.readVarInt();
        this.message = buf.getBuf().readBytes(this.messageSize).array();
    }

    @Override
    public void write(MPKByteBuf buf) {
        buf.writeString(this.moduleId);
        buf.writeVarInt(this.messageSize);
        buf.getBuf().writeBytes(this.message);
    }

    @Override
    public void process(MPKPacketListener listener) {
        listener.handleModuleMessage(this);
    }
}