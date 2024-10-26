package io.github.kurrycat.mpknetapi.common.network.packet;

import io.github.kurrycat.mpknetapi.common.network.packet.impl.MPKPacketListener;
import io.github.kurrycat.mpknetapi.common.network.MPKByteBuf;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.clientbound.MPKPacketDisableModules;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.serverbound.MPKPacketModuleUpdate;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.serverbound.MPKPacketRegister;
import io.github.kurrycat.mpknetapi.common.network.packet.impl.shared.MPKPacketModuleMessage;
import io.netty.buffer.Unpooled;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public abstract class MPKPacket {
    private static final Map<Integer, Class<? extends MPKPacket>> ID_TO_PACKET_MAP = new HashMap<>();
    private static final Map<Class<? extends MPKPacket>, Integer> PACKET_TO_ID_MAP = new HashMap<>();

    private Object attachment;

    public abstract void read(MPKByteBuf buf);
    public abstract void write(MPKByteBuf buf);
    public abstract void process(MPKPacketListener listener);

    public static MPKPacket handle(MPKPacketListener listener, byte[] data, Object attachment) {
        MPKByteBuf buf = new MPKByteBuf(Unpooled.wrappedBuffer(data));

        int id = buf.readVarInt();
        Class<? extends MPKPacket> cPacket = ID_TO_PACKET_MAP.get(id);

        if (cPacket != null) {
            try {
                MPKPacket packet = cPacket.newInstance();
                if (attachment != null) {
                    packet.attach(attachment);
                }
                packet.read(buf);

                return packet;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private static void register(int id, Class<? extends MPKPacket> packet) {
        ID_TO_PACKET_MAP.put(id, packet);
        PACKET_TO_ID_MAP.put(packet, id);
    }

    public byte[] getData() {
        MPKByteBuf buf = new MPKByteBuf(Unpooled.buffer());
        buf.writeVarInt(PACKET_TO_ID_MAP.get(this.getClass()));

        try {
            this.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buf.getBuf().array();
    }

    public void attach(Object attachment) {
        this.attachment = attachment;
    }

    public Object getAttachment() {
        return attachment;
    }

    static {
        register(0, MPKPacketRegister.class);
        register(1, MPKPacketDisableModules.class);
        register(2, MPKPacketModuleUpdate.class);
        register(3, MPKPacketModuleMessage.class);
    }
}
