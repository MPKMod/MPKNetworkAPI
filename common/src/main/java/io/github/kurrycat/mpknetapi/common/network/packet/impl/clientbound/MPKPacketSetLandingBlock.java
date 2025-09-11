package io.github.kurrycat.mpknetapi.common.network.packet.impl.clientbound;

import io.github.kurrycat.mpknetapi.common.network.MPKByteBuf;
import io.github.kurrycat.mpknetapi.common.network.packet.MPKPacket;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.MPKPacketListener;

public class MPKPacketSetLandingBlock extends MPKPacket {

    private int x, y, z;

    public MPKPacketSetLandingBlock() {
    }

    public MPKPacketSetLandingBlock(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void read(MPKByteBuf buf) {
        this.x = buf.readVarInt();
        this.y = buf.readVarInt();
        this.z = buf.readVarInt();
    }

    @Override
    public void write(MPKByteBuf buf) {
        buf.writeVarInt(this.x);
        buf.writeVarInt(this.y);
        buf.writeVarInt(this.z);
    }

    @Override
    public void process(MPKPacketListener listener) {
        ((MPKPacketListenerClient) listener).handleSetLandingBlock(this);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

}
