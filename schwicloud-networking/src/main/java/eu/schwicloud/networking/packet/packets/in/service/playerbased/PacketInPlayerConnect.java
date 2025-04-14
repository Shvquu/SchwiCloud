/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.in.service.playerbased;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;
import lombok.Getter;

public class PacketInPlayerConnect extends Packet {

    @Getter
    private String name;

    @Getter
    private String proxy;

    public PacketInPlayerConnect() {
        setPacketUUID(2995985);
    }

    public PacketInPlayerConnect(String name, String proxy) {
        setPacketUUID(2995985);
        this.name = name;
        this.proxy = proxy;
    }

    public void readPacket(NettyBuffer buffer) {
        this.name = buffer.readString();
        this.proxy = buffer.readString();
    }

    public void writePacket(NettyBuffer buffer) {
        buffer.writeString(this.name);
        buffer.writeString(this.proxy);
    }

}
