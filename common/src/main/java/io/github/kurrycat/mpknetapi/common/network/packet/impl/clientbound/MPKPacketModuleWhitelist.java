package io.github.kurrycat.mpknetapi.common.network.packet.impl.clientbound;

import io.github.kurrycat.mpknetapi.common.network.MPKByteBuf;
import io.github.kurrycat.mpknetapi.common.network.packet.MPKPacket;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.MPKPacketListener;

import java.util.List;

public class MPKPacketModuleWhitelist extends MPKPacket {
    private List<String> whitelist;

    public MPKPacketModuleWhitelist() {
    }

    public MPKPacketModuleWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
    }

    @Override
    public void read(MPKByteBuf buf) {
        this.whitelist = buf.readStringList();
    }

    @Override
    public void write(MPKByteBuf buf) {
        buf.writeStringList(this.whitelist);
    }

    @Override
    public void process(MPKPacketListener listener) {
        ((MPKPacketListenerClient) listener).handleModuleWhitelist(this);
    }

    public List<String> getWhitelist() {
        return whitelist;
    }
}