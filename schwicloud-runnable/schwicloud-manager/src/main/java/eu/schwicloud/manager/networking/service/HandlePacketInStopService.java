package eu.schwicloud.manager.networking.service;

import eu.schwicloud.manager.CloudManager;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.in.service.cloudapi.PacketInStopService;
import io.netty.channel.Channel;

public class HandlePacketInStopService implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInStopService){
            CloudManager.serviceDriver.unregister(((PacketInStopService) packet).getService());
        }

    }
}
