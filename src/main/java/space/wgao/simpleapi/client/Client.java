package space.wgao.simpleapi.client;

import org.eclipse.jetty.websocket.api.Session;
import space.wgao.simpleapi.network.LoginPacket;
import space.wgao.simpleapi.network.Packet;
import space.wgao.simpleapi.network.TextPacket;
import space.wgao.simpleapi.socket.ServiceSocketHandler;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * SimpleApi
 *
 * @author w.gao Copyright (c) 2018.05
 * @version 1.0
 */
public class Client {

    private final Session session;

    private String username = "Guest";

    public Client(Session session) {

        this.session = session;

    }

    public void handleLogin(LoginPacket packet) {

        System.out.println("LOGGED IN: " + packet.username);

        this.username = packet.username;
    }

    public void handleText(TextPacket packet) {

        TextPacket pk = new TextPacket();

        pk.sender = this.username;
        pk.message = packet.message;
        pk.encode();

        ServiceSocketHandler.broadcastPacket(pk);
    }

    public void sendPacket(Packet packet) {

        try {
            this.session.getRemote().sendBytes(ByteBuffer.wrap(packet.buffer));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
