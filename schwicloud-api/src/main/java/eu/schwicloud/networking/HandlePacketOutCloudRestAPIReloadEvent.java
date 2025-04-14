package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.events.listeners.restapi.CloudRestAPIReloadEvent;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.service.events.PacketOutCloudRestAPIReloadEvent;
import io.netty.channel.Channel;

public class HandlePacketOutCloudRestAPIReloadEvent implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutCloudRestAPIReloadEvent){
            CloudAPI.getInstance().getEventDriver().executeEvent(new CloudRestAPIReloadEvent());
        }
    }
}
