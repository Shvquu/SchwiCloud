/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.out.node;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;

public class PacketOutAuthSuccess extends Packet {

    public PacketOutAuthSuccess() {
        setPacketUUID(298234);
    }

    @Override
    public void readPacket(NettyBuffer buffer) {}

    @Override
    public void writePacket(NettyBuffer buffer) {}
}
