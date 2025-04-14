package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.events.listeners.services.CloudProxyCouldNotStartEvent;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.service.events.PacketOutCloudProxyCouldNotStartEvent;
import io.netty.channel.Channel;

public class HandlePacketOutCloudProxyCouldNotStartEvent implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutCloudProxyCouldNotStartEvent){
            CloudAPI.getInstance().getEventDriver().executeEvent(new CloudProxyCouldNotStartEvent(((PacketOutCloudProxyCouldNotStartEvent) packet).getName()));
        }
    }
}
