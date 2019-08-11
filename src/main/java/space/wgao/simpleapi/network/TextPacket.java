package space.wgao.simpleapi.network;

/**
 * SimpleApi
 *
 * @author w.gao Copyright (c) 2018.05
 * @version 1.0
 */
public class TextPacket extends Packet {

    public String sender;
    public String message;

    public byte getID() {
        return CommCode.TEXT;
    }

    public void encode() {
        super.encode();

        this.writeString(this.sender);
        this.writeString(this.message);

    }

    public void decode() {
        super.decode();

        this.sender = this.readString();
        this.message = this.readString();
    }
}
