/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.out.node;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;

public class PacketOutShutdownNode extends Packet {

    public PacketOutShutdownNode() {
        setPacketUUID(90329232);
    }

    @Override
    public void readPacket(NettyBuffer buffer) {

    }

    @Override
    public void writePacket(NettyBuffer buffer) {

    }
}
