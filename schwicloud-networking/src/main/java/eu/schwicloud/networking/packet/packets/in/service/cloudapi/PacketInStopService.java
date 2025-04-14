/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.in.service.cloudapi;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;
import lombok.Getter;

public class PacketInStopService extends Packet {
    @Getter
    private String service;

    public PacketInStopService() {
        setPacketUUID(1278782187);
    }

    public PacketInStopService(String service) {
        setPacketUUID(1278782187);
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
