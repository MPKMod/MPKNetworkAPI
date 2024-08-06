package io.github.kurrycat.mpknetapi.common.network.packet.impl.clientbound;

import io.github.kurrycat.mpknetapi.common.network.packet.MPKPacket;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.MPKPacketListener;
import io.github.kurrycat.mpknetapi.common.network.MPKByteBuf;

import java.util.List;

public class MPKPacketModuleBlacklist extends MPKPacket {
    private List<String> blacklist;

    public MPKPacketModuleBlacklist() {
    }

    public MPKPacketModuleBlacklist(List<String> blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public void read(MPKByteBuf buf) {
        this.blacklist = buf.readStringList();
    }

    @Override
    public void write(MPKByteBuf buf) {
        buf.writeStringList(this.blacklist);
    }

    @Override
    public void process(MPKPacketListener listener) {
        ((MPKPacketListenerClient) listener).handleModuleBlacklist(this);
    }

    public List<String> getBlacklist() {
        return blacklist;
    }
}
