package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.events.listeners.restapi.CloudRestAPIUpdateEvent;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.service.events.PacketOutCloudRestAPIUpdateEvent;
import io.netty.channel.Channel;

public class HandlePacketOutCloudRestAPIUpdateEvent implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutCloudRestAPIUpdateEvent){
            CloudAPI.getInstance().getEventDriver().executeEvent(new CloudRestAPIUpdateEvent(((PacketOutCloudRestAPIUpdateEvent) packet).getPath(), ((PacketOutCloudRestAPIUpdateEvent) packet).getContent()));
        }
    }
}
