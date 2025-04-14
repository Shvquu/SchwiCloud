/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.in.service.playerbased.apibased;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;
import lombok.Getter;

public class PacketInAPIPlayerConnect extends Packet {

    @Getter
    private String username;
    @Getter
    private String service;

    public PacketInAPIPlayerConnect() {
        setPacketUUID(10220230);
    }

    public PacketInAPIPlayerConnect(String username, String service) {
        setPacketUUID(10220230);
        this.username = username;
        this.service = service;
    }

    @Override
    public void readPacket(NettyBuffer buffer) {
        this.username = buffer.readString();
        this.service = buffer.readString();
    }

    @Override
    public void writePacket(NettyBuffer buffer) {
        buffer.writeString(username);
        buffer.writeString(service);
    }
}
