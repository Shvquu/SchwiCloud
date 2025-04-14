/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.out.node;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;

public class PacketOutDisableNodeConsole extends Packet {


    public PacketOutDisableNodeConsole() {
        setPacketUUID(328993212);
    }

    @Override
    public void readPacket(NettyBuffer buffer) {

    }

    @Override
    public void writePacket(NettyBuffer buffer) {

    }
}
