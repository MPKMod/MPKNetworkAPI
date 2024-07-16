package io.github.kurrycat2004.mpknetapi.common.network.packet.impl.clientbound;

import io.github.kurrycat2004.mpknetapi.common.network.MPKByteBuf;
import io.github.kurrycat2004.mpknetapi.common.network.packet.MPKPacket;
import io.github.kurrycat2004.mpknetapi.common.network.packet.impl.MPKPacketListener;

import java.util.List;

public class MPKPacketModuleBlacklist extends MPKPacket {
    private List<String> idBlocklist;

    public MPKPacketModuleBlacklist() {
    }

    public MPKPacketModuleBlacklist(List<String> idBlocklist) {
        this.idBlocklist = idBlocklist;
    }

    @Override
    public void read(MPKByteBuf buf) {
        this.idBlocklist = buf.readStringList();
    }

    @Override
    public void write(MPKByteBuf buf) {
        buf.writeStringList(this.idBlocklist);
    }

    @Override
    public void process(MPKPacketListener listener) {
        ((MPKPacketListenerClient) listener).handleModuleBlacklist(this);
    }
}
