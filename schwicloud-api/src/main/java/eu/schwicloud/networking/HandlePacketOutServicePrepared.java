package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.events.listeners.services.CloudProxyPreparedEvent;
import eu.schwicloud.events.listeners.services.CloudServicePreparedEvent;
import eu.schwicloud.networking.packet.packets.out.service.PacketOutServicePrepared;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketOutServicePrepared implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutServicePrepared){
            if (((PacketOutServicePrepared) packet).isProxy()){
                CloudAPI.getInstance().getEventDriver().executeEvent(new CloudProxyPreparedEvent(((PacketOutServicePrepared) packet).getName(), ((PacketOutServicePrepared) packet).getGroup(), ((PacketOutServicePrepared) packet).getNode()));
            }else {
                CloudAPI.getInstance().getEventDriver().executeEvent(new CloudServicePreparedEvent(((PacketOutServicePrepared) packet).getName(), ((PacketOutServicePrepared) packet).getGroup(), ((PacketOutServicePrepared) packet).getNode()));
            }
        }
    }
}
