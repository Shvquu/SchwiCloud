package eu.schwicloud.manager.networking.service;

import eu.schwicloud.networking.NettyDriver;
import eu.schwicloud.networking.packet.packets.in.service.playerbased.apibased.PacketInAPIPlayerTab;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.apibased.PacketOutAPIPlayerTab;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketInAPIPlayerTab implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInAPIPlayerTab){
            NettyDriver.getInstance().nettyServer.sendToAllAsynchronous(new PacketOutAPIPlayerTab(((PacketInAPIPlayerTab) packet).getUsername(), ((PacketInAPIPlayerTab) packet).getHeader(), ((PacketInAPIPlayerTab) packet).getFooter()));
        }
    }
}
