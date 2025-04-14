package eu.schwicloud.manager.networking.service;

import eu.schwicloud.networking.NettyDriver;
import eu.schwicloud.networking.packet.packets.in.service.playerbased.apibased.PacketInCloudPlayerComponent;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.apibased.PacketOutCloudPlayerComponent;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketInCloudPlayerComponent implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInCloudPlayerComponent){
            NettyDriver.getInstance().nettyServer.sendToAllAsynchronous(new PacketOutCloudPlayerComponent(((PacketInCloudPlayerComponent) packet).getComponent(), ((PacketInCloudPlayerComponent) packet).getPlayer()));
        }
    }
}
