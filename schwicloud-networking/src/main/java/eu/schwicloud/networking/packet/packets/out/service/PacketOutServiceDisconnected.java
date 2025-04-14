/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.out.service;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;
import lombok.Getter;

public class PacketOutServiceDisconnected extends Packet {

    @Getter
    private String name;
    @Getter
    private boolean isProxy;


    public PacketOutServiceDisconnected() {
        setPacketUUID(291234);
    }

    public PacketOutServiceDisconnected(String name, boolean isProxy) {
        setPacketUUID(291234);
        this.name = name;
        this.isProxy = isProxy;
    }

    @Override
    public void readPacket(NettyBuffer buffer) {
        name = buffer.readString();
        isProxy = buffer.readBoolean();
    }

    @Override
    public void writePacket(NettyBuffer buffer) {
        buffer.writeString(name);
        buffer.writeBoolean(isProxy);
    }
}
