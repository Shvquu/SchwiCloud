/*
 * this class is by RauchigesEtwas
 */

/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.in.service.command;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;
import lombok.Getter;

public class PacketInCommandMaxPlayers extends Packet {

    @Getter
    private String group;
    @Getter
    private Integer amount;

    public PacketInCommandMaxPlayers(String group, Integer amount) {
        setPacketUUID(918439129);
        this.group = group;
        this.amount = amount;
    }

    public PacketInCommandMaxPlayers() {
        setPacketUUID(918439129);
    }

    @Override
    public void readPacket(NettyBuffer buffer) {
        this.group = buffer.readString();
        this.amount = buffer.readInt();
    }

    @Override
    public void writePacket(NettyBuffer buffer) {
        buffer.writeString(this.group);
        buffer.writeInt(this.amount);
    }


}
