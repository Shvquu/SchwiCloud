package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.events.listeners.services.CloudProxyChangeStateEvent;
import eu.schwicloud.networking.packet.packets.out.service.PacketOutCloudProxyChangeState;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketOutCloudProxyChangeState implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutCloudProxyChangeState){
            CloudAPI.getInstance().getEventDriver().executeEvent(new CloudProxyChangeStateEvent(((PacketOutCloudProxyChangeState) packet).getName(), ((PacketOutCloudProxyChangeState) packet).getState()));
        }
    }
}
