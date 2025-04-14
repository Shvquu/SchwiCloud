package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.events.listeners.services.CloudServiceCouldNotStartEvent;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.service.events.PacketOutCloudServiceCouldNotStartEvent;
import io.netty.channel.Channel;

public class HandlePacketOutCloudServiceCouldNotStartEvent implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutCloudServiceCouldNotStartEvent){
            CloudAPI.getInstance().getEventDriver().executeEvent(new CloudServiceCouldNotStartEvent(((PacketOutCloudServiceCouldNotStartEvent) packet).getName()));
        }
    }
}
