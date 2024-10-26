package io.github.kurrycat.mpknetapi.common.network.packet.impl.serverbound;

import io.github.kurrycat.mpknetapi.common.network.packet.MPKPacket;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.MPKPacketListener;
import io.github.kurrycat.mpknetapi.common.network.MPKByteBuf;

import java.util.List;

@SuppressWarnings("unused")
public class MPKPacketModuleUpdate extends MPKPacket {
    private List<String> newModules;

    public MPKPacketModuleUpdate() {
    }

    public MPKPacketModuleUpdate(List<String> newModules) {
        this.newModules = newModules;
    }

    @Override
    public void read(MPKByteBuf buf) {
        this.newModules = buf.readStringList();
    }

    @Override
    public void write(MPKByteBuf buf) {
        buf.writeStringList(this.newModules);
    }

    @Override
    public void process(MPKPacketListener listener) {
        ((MPKPacketListenerServer) listener).handleModuleUpdate(this);
    }

    public List<String> getNewModules() {
        return newModules;
    }
}
