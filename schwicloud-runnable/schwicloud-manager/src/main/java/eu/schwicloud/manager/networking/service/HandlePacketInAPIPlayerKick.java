package eu.schwicloud.manager.networking.service;

import eu.schwicloud.networking.NettyDriver;
import eu.schwicloud.networking.packet.packets.in.service.playerbased.apibased.PacketInAPIPlayerKick;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.apibased.PacketOutAPIPlayerKick;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketInAPIPlayerKick implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {

        if (packet instanceof PacketInAPIPlayerKick){
            NettyDriver.getInstance().nettyServer.sendToAllAsynchronous(new PacketOutAPIPlayerKick(((PacketInAPIPlayerKick) packet).getUsername(), ((PacketInAPIPlayerKick) packet).getMessage()));
        }

    }


}
