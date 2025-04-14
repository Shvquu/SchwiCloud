package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.events.listeners.services.CloudProxyLaunchEvent;
import eu.schwicloud.events.listeners.services.CloudServiceLaunchEvent;
import eu.schwicloud.networking.packet.packets.out.service.PacketOutServiceLaunch;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketOutServiceLaunch implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutServiceLaunch){
            if (((PacketOutServiceLaunch) packet).isProxy()){

                CloudAPI.getInstance().getEventDriver().executeEvent(new CloudProxyLaunchEvent(((PacketOutServiceLaunch) packet).getName(), ((PacketOutServiceLaunch) packet).getGroup(), ((PacketOutServiceLaunch) packet).getNode()));
            }else {
                CloudAPI.getInstance().getEventDriver().executeEvent(new CloudServiceLaunchEvent(((PacketOutServiceLaunch) packet).getName(), ((PacketOutServiceLaunch) packet).getGroup(), ((PacketOutServiceLaunch) packet).getNode()));
            }
        }
    }
}
