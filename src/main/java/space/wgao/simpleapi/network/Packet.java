package space.wgao.simpleapi.network;

import space.wgao.simpleapi.utils.Binary;

import java.util.Arrays;

/**
 * SimpleApi
 *
 * @author w.gao Copyright (c) 2018.05
 * @version 1.0
 */
public abstract class Packet {

    protected int offset = 0;
    public byte[] buffer;

    public abstract byte getID();

    protected byte[] read(int len) {
        if (len < 0) {
            this.offset = this.buffer.length - 1;
            return new byte[0];
        }

        byte[] buffer = new byte[len];
        for (int i = 0; i < len; i++) {
            buffer[i] = this.buffer[this.offset++];
        }
        return buffer;
    }

    protected byte[] read() {
        try {
            return Arrays.copyOfRange(this.buffer, this.offset, this.buffer.length - 1);
        } catch (Exception e) {
            return new byte[0];
        }
    }

    protected byte readByte() {
        return this.buffer[this.offset++];
    }

    protected int readInt() {
        return Binary.readInt(this.read(4));
    }

    protected int readShort() {
        return Binary.readShort(this.read(2));
    }

    protected float readFloat() {
        return Binary.readFloat(this.read(4));
    }

    protected double readDouble() {
        return Binary.readDouble(this.read(4));
    }

    protected String readString() {

        int length = this.readShort();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < length; i++) {
            str.append((char) this.readShort());
        }
        return str.toString();
    }

    protected boolean eof() {
        return !(this.offset >= 0 && this.offset + 1 <= this.buffer.length);
    }

    protected void write(byte[] b) {
        this.buffer = Binary.appendBytes(this.buffer, b);
    }


    protected void writeByte(byte b) {
        byte[] newBytes = new byte[this.buffer.length + 1];
        System.arraycopy(this.buffer, 0, newBytes, 0, this.buffer.length);
        newBytes[this.buffer.length] = b;
        this.buffer = newBytes;
    }

    protected void writeInt(int v) {
        this.write(Binary.writeInt(v));
    }

    protected void writeShort(int v) {
        this.write(Binary.writeShort(v));
    }
    protected void writeFloat(float f) {
        this.write(Binary.writeFloat(f));
    }

    protected void writeDouble(double d) {
        this.write(Binary.writeDouble(d));
    }

    protected void writeString(String str) {

        this.writeShort(str.length());
        for (char c : str.toCharArray()) {
            this.writeShort(c);
        }
    }

    public void encode() {
        this.buffer = new byte[]{getID()};
    }

    public void decode() {
        this.offset = 1;
    }

}
