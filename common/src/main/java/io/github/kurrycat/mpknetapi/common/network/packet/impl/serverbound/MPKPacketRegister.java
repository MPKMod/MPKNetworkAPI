package io.github.kurrycat.mpknetapi.common.network.packet.impl.serverbound;

import io.github.kurrycat.mpknetapi.common.network.packet.MPKPacket;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.MPKPacketListener;
import io.github.kurrycat.mpknetapi.common.network.MPKByteBuf;

import java.util.List;

@SuppressWarnings("unused")
public class MPKPacketRegister extends MPKPacket {
    private String modVersion;
    private List<String> loadedModules;

    public MPKPacketRegister() {
    }

    public MPKPacketRegister(String modVersion, List<String> loadedModules) {
        this.modVersion = modVersion;
        this.loadedModules = loadedModules;
    }

    @Override
    public void read(MPKByteBuf buf) {
        this.modVersion = buf.readString();
        this.loadedModules = buf.readStringList();
    }

    @Override
    public void write(MPKByteBuf buf) {
        buf.writeString(this.modVersion);
        buf.writeStringList(this.loadedModules);
    }

    @Override
    public void process(MPKPacketListener listener) {
        ((MPKPacketListenerServer) listener).handleRegister(this);
    }

    public String getModVersion() {
        return modVersion;
    }

    public List<String> getLoadedModules() {
        return loadedModules;
    }
}