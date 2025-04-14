/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.in.service;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;
import lombok.Getter;

public class PacketInServiceDisconnect extends Packet {
    @Getter
    private String service;

    public PacketInServiceDisconnect() {
        setPacketUUID(241212);
    }

    public PacketInServiceDisconnect(String service) {
        setPacketUUID(241212);
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
