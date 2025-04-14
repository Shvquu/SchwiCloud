/*
 * this class is by RauchigesEtwas
 */

/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.in.service.cloudapi;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;
import lombok.Getter;

public class PacketInDeleteGroup extends Packet {
    @Getter
    private String group;

    public PacketInDeleteGroup() {
        setPacketUUID(98034934);
    }


    public PacketInDeleteGroup(String group) {
        setPacketUUID(98034934);
        this.group = group;
    }


    @Override
    public void readPacket(NettyBuffer buffer) {
        group = buffer.readString();
    }

    @Override
    public void writePacket(NettyBuffer buffer) {
        buffer.writeString(group);
    }
}
