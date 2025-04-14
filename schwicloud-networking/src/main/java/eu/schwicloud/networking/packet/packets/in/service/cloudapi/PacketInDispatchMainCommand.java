/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.in.service.cloudapi;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;
import lombok.Getter;

public class PacketInDispatchMainCommand extends Packet {

    @Getter
    private String command;

    public PacketInDispatchMainCommand() {
        setPacketUUID(53123);
    }

    public PacketInDispatchMainCommand(String command) {
        setPacketUUID(53123);
        this.command = command;
    }

    @Override
    public void readPacket(NettyBuffer buffer) {
        command = buffer.readString();
    }

    @Override
    public void writePacket(NettyBuffer buffer) {
        buffer.writeString(command);
    }
}
