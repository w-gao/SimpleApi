package space.wgao.simpleapi.network;

/**
 * SimpleApi
 *
 * @author w.gao Copyright (c) 2018.05
 * @version 1.0
 */
public class LoginPacket extends Packet {

    public String username;

    public byte getID() {
        return CommCode.LOGIN;
    }

    public void decode() {
        super.decode();

        this.username = this.readString();
    }
}
