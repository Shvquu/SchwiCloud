package eu.schwicloud.manager.networking.service;

import eu.schwicloud.manager.CloudManager;
import eu.schwicloud.networking.packet.packets.in.service.cloudapi.PacketInDispatchCommand;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketInDispatchCommand implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof  PacketInDispatchCommand){
            CloudManager.serviceDriver.getService(((PacketInDispatchCommand) packet).getService()).handelExecute(((PacketInDispatchCommand) packet).getCommand());
        }
    }
}
