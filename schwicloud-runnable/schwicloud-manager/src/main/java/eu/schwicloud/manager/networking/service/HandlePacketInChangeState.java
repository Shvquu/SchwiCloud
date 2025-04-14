package eu.schwicloud.manager.networking.service;

import eu.schwicloud.manager.CloudManager;
import eu.schwicloud.networking.packet.packets.in.service.cloudapi.PacketInChangeState;
import eu.schwicloud.process.ServiceState;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketInChangeState implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInChangeState){
            CloudManager.serviceDriver.getService(((PacketInChangeState) packet).getService()).handelStatusChange(ServiceState.valueOf(((PacketInChangeState) packet).getState()));
        }
    }
}
