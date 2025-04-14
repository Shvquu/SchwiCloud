package eu.schwicloud.networking.packet.packets.out.service.events;

import eu.schwicloud.networking.packet.NettyBuffer;
import eu.schwicloud.networking.packet.Packet;
import org.jetbrains.annotations.NotNull;

public class PacketOutCloudRestAPIDeleteEvent extends Packet {

    @lombok.Getter
    private String path;

    public PacketOutCloudRestAPIDeleteEvent() {
        setPacketUUID(323233122);
    }

    public PacketOutCloudRestAPIDeleteEvent(String path) {
        setPacketUUID(323233122);
        this.path = path;
    }

    @Override
    public void readPacket(@NotNull NettyBuffer buffer) {

    }

    @Override
    public void writePacket(@NotNull NettyBuffer buffer) {

    }
}
