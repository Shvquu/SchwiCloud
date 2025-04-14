package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.events.listeners.group.CloudGroupUpdateEditEvent;
import eu.schwicloud.networking.packet.packets.out.service.group.PacketOutGroupEdit;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketOutGroupEdit implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutGroupEdit){
            CloudAPI.getInstance().getEventDriver().executeEvent(new CloudGroupUpdateEditEvent(((PacketOutGroupEdit) packet).getGroup()));
        }
    }
}
