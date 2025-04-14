package eu.schwicloud.manager.networking.service;

import eu.schwicloud.manager.CloudManager;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.in.service.cloudapi.PacketInStopGroup;
import io.netty.channel.Channel;

public class HandlePacketInStopGroup implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInStopGroup){
            CloudManager.serviceDriver.getServices(((PacketInStopGroup) packet).getGroup()).forEach(taskedService -> CloudManager.serviceDriver.unregister(taskedService.getEntry().getServiceName()));
        }
    }
}
