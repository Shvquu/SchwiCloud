package eu.schwicloud.manager.networking.service;

import eu.schwicloud.networking.NettyDriver;
import eu.schwicloud.networking.packet.packets.in.service.playerbased.apibased.PacketInAPIPlayerConnect;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.apibased.PacketOutAPIPlayerConnect;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketInAPIPlayerConnect implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInAPIPlayerConnect){
            NettyDriver.getInstance().nettyServer.sendToAllSynchronized(new PacketOutAPIPlayerConnect(((PacketInAPIPlayerConnect) packet).getUsername(), ((PacketInAPIPlayerConnect) packet).getService()));
        }
    }
}
