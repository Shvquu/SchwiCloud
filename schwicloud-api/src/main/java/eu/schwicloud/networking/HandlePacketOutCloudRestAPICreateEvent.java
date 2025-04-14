package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.events.listeners.restapi.CloudRestAPICreateEvent;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.service.events.PacketOutCloudRestAPICreateEvent;
import io.netty.channel.Channel;

public class HandlePacketOutCloudRestAPICreateEvent implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutCloudRestAPICreateEvent){
            CloudAPI.getInstance().getEventDriver().executeEvent(new CloudRestAPICreateEvent(((PacketOutCloudRestAPICreateEvent) packet).getPath(), ((PacketOutCloudRestAPICreateEvent) packet).getContent()));
        }
    }
}
