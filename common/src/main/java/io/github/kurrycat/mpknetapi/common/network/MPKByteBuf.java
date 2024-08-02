package io.github.kurrycat.mpknetapi.common.network;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MPKByteBuf {
    private final ByteBuf buf;

    public MPKByteBuf(ByteBuf buf) {
        this.buf = buf;
    }

    public int readVarInt() {
        int result = 0;
        int byteCount = 0;

        byte currentByte;
        do {
            currentByte = this.buf.readByte();
            result |= (currentByte & 127) << byteCount++ * 7;
            if (byteCount > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while((currentByte & 128) == 128);

        return result;
    }

    public void writeVarInt(int input) {
        while((input & -128) != 0) {
            this.buf.writeByte(input & 127 | 128);
            input >>>= 7;
        }

        this.buf.writeByte(input);
    }

    public String readString() {
        int maxLength = Short.MAX_VALUE;
        int strLength = this.readVarInt();
        if (strLength > maxLength) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + strLength + " > " + maxLength + ")");
        } else if (strLength < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        } else {
            String string = buf.toString(buf.readerIndex(), strLength, StandardCharsets.UTF_8);
            buf.readerIndex(buf.readerIndex() + strLength);
            if (string.length() > maxLength) {
                throw new DecoderException("The received string length is longer than maximum allowed (" + strLength + " > " + maxLength + ")");
            } else {
                return string;
            }
        }
    }

    public void writeString(String string) {
        byte[] bs = string.getBytes(StandardCharsets.UTF_8);
        if (bs.length > 32767) {
            throw new EncoderException("String too big (was " + string.length() + " bytes encoded, max " + 32767 + ")");
        } else {
            this.writeVarInt(bs.length);
            this.buf.writeBytes(bs);
        }
    }

    public List<String> readStringList() {
        int size = this.readVarInt();
        List<String> output = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            output.add(this.readString());
        }

        return output;
    }

    public void writeStringList(List<String> strings) {
        this.writeVarInt(strings.size());
        strings.forEach(this::writeString);
    }

    public ByteBuf getBuf() {
        return buf;
    }
}
