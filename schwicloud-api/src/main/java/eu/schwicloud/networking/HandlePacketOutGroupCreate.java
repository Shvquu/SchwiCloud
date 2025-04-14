package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.events.listeners.group.CloudGroupCreateEvent;
import eu.schwicloud.networking.packet.packets.out.service.group.PacketOutGroupCreate;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketOutGroupCreate  implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutGroupCreate){
            CloudAPI.getInstance().getEventDriver().executeEvent(new CloudGroupCreateEvent(((PacketOutGroupCreate) packet).getGroup()));
        }
    }
}
