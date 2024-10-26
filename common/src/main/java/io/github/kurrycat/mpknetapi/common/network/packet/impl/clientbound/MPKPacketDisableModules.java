package io.github.kurrycat.mpknetapi.common.network.packet.impl.clientbound;

import io.github.kurrycat.mpknetapi.common.network.MPKByteBuf;
import io.github.kurrycat.mpknetapi.common.network.packet.MPKPacket;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.MPKPacketListener;

import java.util.List;

@SuppressWarnings("unused")
public class MPKPacketDisableModules extends MPKPacket {
    List<String> modulesToDisable;

    public MPKPacketDisableModules() {
    }

    public MPKPacketDisableModules(List<String> modulesToDisable) {
        this.modulesToDisable = modulesToDisable;
    }

    @Override
    public void read(MPKByteBuf buf) {
        this.modulesToDisable = buf.readStringList();
    }

    @Override
    public void write(MPKByteBuf buf) {
        buf.writeStringList(this.modulesToDisable);
    }

    @Override
    public void process(MPKPacketListener listener) {
        ((MPKPacketListenerClient) listener).handleDisableModules(this);
    }

    public List<String> getModulesToDisable() {
        return modulesToDisable;
    }
}
