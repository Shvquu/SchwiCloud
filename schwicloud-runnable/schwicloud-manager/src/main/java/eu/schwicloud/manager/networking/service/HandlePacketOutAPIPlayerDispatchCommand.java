package eu.schwicloud.manager.networking.service;

import eu.schwicloud.networking.NettyDriver;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.in.service.playerbased.apibased.PacketOutAPIPlayerDispactchCommand;
import io.netty.channel.Channel;

public class HandlePacketOutAPIPlayerDispatchCommand implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutAPIPlayerDispactchCommand){
            NettyDriver.getInstance().nettyServer.sendToAllSynchronized(new PacketOutAPIPlayerDispactchCommand(((PacketOutAPIPlayerDispactchCommand) packet).getUserName(), ((PacketOutAPIPlayerDispactchCommand) packet).getCommand()));
        }
    }
}
