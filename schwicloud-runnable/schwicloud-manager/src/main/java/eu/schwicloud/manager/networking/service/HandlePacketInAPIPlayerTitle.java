package eu.schwicloud.manager.networking.service;

import eu.schwicloud.networking.NettyDriver;
import eu.schwicloud.networking.packet.packets.in.service.playerbased.apibased.PacketInAPIPlayerTitle;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.apibased.PacketOutAPIPlayerTitle;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketInAPIPlayerTitle implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInAPIPlayerTitle){
            NettyDriver.getInstance().nettyServer.sendToAllAsynchronous(new PacketOutAPIPlayerTitle(((PacketInAPIPlayerTitle) packet).getTitle(), ((PacketInAPIPlayerTitle) packet).getSubTitle(), ((PacketInAPIPlayerTitle) packet).getFadeIn(), ((PacketInAPIPlayerTitle) packet).getStay(), ((PacketInAPIPlayerTitle) packet).getFadeOut(), ((PacketInAPIPlayerTitle) packet).getUsername()));
        }
    }
}
