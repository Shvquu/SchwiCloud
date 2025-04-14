/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.in.service.playerbased;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;
import lombok.Getter;

public class PacketInPlayerDisconnect extends Packet {

    @Getter
    private String name;

    public PacketInPlayerDisconnect() {
        setPacketUUID(192351);
    }

    public PacketInPlayerDisconnect(String name) {
        setPacketUUID(192351);
        this.name = name;
    }

    public void readPacket(NettyBuffer buffer) {
        this.name = buffer.readString();
    }

    public void writePacket(NettyBuffer buffer) {
        buffer.writeString(this.name);
    }

}
