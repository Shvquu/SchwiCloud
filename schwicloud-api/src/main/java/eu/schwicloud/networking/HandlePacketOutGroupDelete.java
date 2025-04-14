package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.events.listeners.group.CloudGroupDeleteEvent;
import eu.schwicloud.networking.packet.packets.out.service.group.PacketOutGroupDelete;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketOutGroupDelete implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutGroupDelete){
            CloudAPI.getInstance().getEventDriver().executeEvent(new CloudGroupDeleteEvent(((PacketOutGroupDelete) packet).getGroup()));
        }
    }
}
