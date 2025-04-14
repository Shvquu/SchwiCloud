package eu.schwicloud.manager.networking.service;

import eu.schwicloud.networking.NettyDriver;
import eu.schwicloud.networking.packet.packets.in.service.playerbased.apibased.PacketInAPIPlayerMessage;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.apibased.PacketOutAPIPlayerMessage;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketInAPIPlayerMessage implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInAPIPlayerMessage){
            NettyDriver.getInstance().nettyServer.sendToAllAsynchronous(new PacketOutAPIPlayerMessage(((PacketInAPIPlayerMessage) packet).getUsername(), ((PacketInAPIPlayerMessage) packet).getMessage()));
        }
    }
}
