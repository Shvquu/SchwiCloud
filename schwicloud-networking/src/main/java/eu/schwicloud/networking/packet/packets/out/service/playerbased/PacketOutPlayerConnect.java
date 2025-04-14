/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.out.service.playerbased;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;
import lombok.Getter;

public class PacketOutPlayerConnect extends Packet {

    @Getter
    private String name;
    @Getter
    private String proxy;

    public PacketOutPlayerConnect() {
        setPacketUUID(423293);
    }

    public PacketOutPlayerConnect(String name, String proxy) {
        setPacketUUID(423293);
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
