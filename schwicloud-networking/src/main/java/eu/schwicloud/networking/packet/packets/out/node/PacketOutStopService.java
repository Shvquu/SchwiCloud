/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.out.node;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;
import lombok.Getter;

public class PacketOutStopService extends Packet {

    @Getter
    private String service;

    public PacketOutStopService() {
        setPacketUUID(87432921);
    }

    public PacketOutStopService(String service) {
        setPacketUUID(87432921);
        this.service = service;
    }
    @Override
    public void readPacket(NettyBuffer buffer) {
        this.service = buffer.readString();
    }

    @Override
    public void writePacket(NettyBuffer buffer) {
        buffer.writeString(this.service);
    }
}
