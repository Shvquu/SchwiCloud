package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.events.listeners.restapi.CloudRestAPIDeleteEvent;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.service.events.PacketOutCloudRestAPIDeleteEvent;
import io.netty.channel.Channel;

public class HandlePacketOutCloudRestAPIDeleteEvent implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutCloudRestAPIDeleteEvent){
            CloudAPI.getInstance().getEventDriver().executeEvent(new CloudRestAPIDeleteEvent(((PacketOutCloudRestAPIDeleteEvent) packet).getPath()));
        }
    }
}
