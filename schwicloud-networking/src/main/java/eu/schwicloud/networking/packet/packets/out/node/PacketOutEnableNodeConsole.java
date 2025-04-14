/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.out.node;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;

public class PacketOutEnableNodeConsole extends Packet {

    public PacketOutEnableNodeConsole() {
        setPacketUUID(191230131);
    }

    @Override
    public void readPacket(NettyBuffer buffer) {

    }

    @Override
    public void writePacket(NettyBuffer buffer) {

    }
}
