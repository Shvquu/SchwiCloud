/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.out.node;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;
import lombok.Getter;

public class PacketOutSyncService extends Packet {

    @Getter
    private String service;

    public PacketOutSyncService() {
        setPacketUUID(38676819);
    }

    public PacketOutSyncService(String service) {
        setPacketUUID(38676819);
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
