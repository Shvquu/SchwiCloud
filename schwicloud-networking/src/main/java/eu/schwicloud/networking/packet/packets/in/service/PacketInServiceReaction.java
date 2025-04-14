/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.in.service;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;
import lombok.Getter;

public class PacketInServiceReaction extends Packet {

    @Getter
    private String  service;


    public PacketInServiceReaction() {
        setPacketUUID(2209492);
    }

    public PacketInServiceReaction(String service) {
        setPacketUUID(2209492);
        this.service = service;
    }

    @Override
    public void readPacket(NettyBuffer buffer) {
        service = buffer.readString();
    }

    @Override
    public void writePacket(NettyBuffer buffer) {
            buffer.writeString(service);
    }
}
