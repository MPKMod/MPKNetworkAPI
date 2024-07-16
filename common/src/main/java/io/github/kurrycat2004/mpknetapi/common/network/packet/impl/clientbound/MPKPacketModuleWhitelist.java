package io.github.kurrycat2004.mpknetapi.common.network.packet.impl.clientbound;

import io.github.kurrycat2004.mpknetapi.common.network.MPKByteBuf;
import io.github.kurrycat2004.mpknetapi.common.network.packet.MPKPacket;
import io.github.kurrycat2004.mpknetapi.common.network.packet.impl.MPKPacketListener;

import java.util.List;

public class MPKPacketModuleWhitelist extends MPKPacket {
    private List<String> idWhitelist;

    public MPKPacketModuleWhitelist() {
    }

    public MPKPacketModuleWhitelist(List<String> idWhitelist) {
        this.idWhitelist = idWhitelist;
    }

    @Override
    public void read(MPKByteBuf buf) {
        this.idWhitelist = buf.readStringList();
    }

    @Override
    public void write(MPKByteBuf buf) {
        buf.writeStringList(this.idWhitelist);
    }

    @Override
    public void process(MPKPacketListener listener) {
        ((MPKPacketListenerClient) listener).handleModuleWhitelist(this);
    }
}