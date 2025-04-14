/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.networking.packet.packets.out.node;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;

public class PacketOutDisableConsole extends Packet {

    private String service;


    public PacketOutDisableConsole() {
        setPacketUUID(2323244);
    }

    public PacketOutDisableConsole(String service) {
        setPacketUUID(2323244);
        this.service = service;
    }

    @Override
    public void readPacket(NettyBuffer buffer) {
        service = buffer.readString();
    }

    @Override
    public void writePacket(NettyBuffer buffer) {
        buffer.writeString(service);
    }

    public String getService() {
        return service;
    }
}
