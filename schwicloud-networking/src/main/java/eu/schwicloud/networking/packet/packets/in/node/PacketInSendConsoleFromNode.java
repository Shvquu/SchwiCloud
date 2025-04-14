/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.in.node;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;
import lombok.Getter;

public class PacketInSendConsoleFromNode extends Packet {

    @Getter
    private String line;

    public PacketInSendConsoleFromNode() {
        setPacketUUID(29123812);
    }

    public PacketInSendConsoleFromNode(String line) {
        setPacketUUID(29123812);
        this.line = line;
    }

    @Override
    public void readPacket(NettyBuffer buffer) {
        this.line = buffer.readString();
    }

    @Override
    public void writePacket(NettyBuffer buffer) {
        buffer.writeString(this.line);
    }
}
