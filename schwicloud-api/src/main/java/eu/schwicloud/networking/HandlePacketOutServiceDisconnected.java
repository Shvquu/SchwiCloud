package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.events.listeners.services.CloudProxyDisconnectedEvent;
import eu.schwicloud.events.listeners.services.CloudServiceDisconnectedEvent;
import eu.schwicloud.networking.packet.packets.out.service.PacketOutServiceDisconnected;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketOutServiceDisconnected implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutServiceDisconnected){
            if (((PacketOutServiceDisconnected) packet).isProxy()){
                CloudAPI.getInstance().getEventDriver().executeEvent(new CloudProxyDisconnectedEvent(((PacketOutServiceDisconnected) packet).getName()));
            }else {
                CloudAPI.getInstance().getEventDriver().executeEvent(new CloudServiceDisconnectedEvent(((PacketOutServiceDisconnected) packet).getName()));
            }


            CloudAPI.getInstance().getServicePool().unregisterService(((PacketOutServiceDisconnected) packet).getName());
            CloudAPI.getInstance().getAsyncServicePool().unregisterService(((PacketOutServiceDisconnected) packet).getName());
        }
    }
}
