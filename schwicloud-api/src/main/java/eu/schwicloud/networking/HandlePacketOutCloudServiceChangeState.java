package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.events.listeners.services.CloudServiceChangeStateEvent;
import eu.schwicloud.networking.packet.packets.out.service.PacketOutCloudServiceChangeState;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketOutCloudServiceChangeState implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutCloudServiceChangeState){
            CloudAPI.getInstance().getEventDriver().executeEvent(new CloudServiceChangeStateEvent(((PacketOutCloudServiceChangeState) packet).getName(), ((PacketOutCloudServiceChangeState) packet).getState()));
        }
    }
}
