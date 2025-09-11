package io.github.kurrycat.mpknetapi.common.network.packet.impl.clientbound;

import io.github.kurrycat.mpknetapi.common.network.MPKByteBuf;
import io.github.kurrycat.mpknetapi.common.network.packet.MPKPacket;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.MPKPacketListener;

public class MPKPacketSetLandingBlock extends MPKPacket {

    private int x, y, z;
    private LandingMode landingMode;

    public MPKPacketSetLandingBlock() {
    }

    public MPKPacketSetLandingBlock(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.landingMode = LandingMode.LAND;
    }

    public MPKPacketSetLandingBlock(int x, int y, int z, LandingMode landingMode) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.landingMode = landingMode;
    }

    @Override
    public void read(MPKByteBuf buf) {
        this.x = buf.readVarInt();
        this.y = buf.readVarInt();
        this.z = buf.readVarInt();
        this.landingMode = LandingMode.values()[buf.readVarInt()];
    }

    @Override
    public void write(MPKByteBuf buf) {
        buf.writeVarInt(this.x);
        buf.writeVarInt(this.y);
        buf.writeVarInt(this.z);
        buf.writeVarInt(this.landingMode.ordinal());
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

    public LandingMode getLandingMode() {
        return landingMode;
    }

    public int getLandingModeId() {
        return landingMode.ordinal();
    }

    public enum LandingMode {
        LAND,
        HIT,
        Z_NEO,
        ENTER
    }

}
