package eu.schwicloud.manager.networking.service;

import eu.schwicloud.Driver;
import eu.schwicloud.events.listeners.services.CloudProxyDisconnectedEvent;
import eu.schwicloud.events.listeners.services.CloudServiceDisconnectedEvent;
import eu.schwicloud.groups.dummy.Group;
import eu.schwicloud.manager.CloudManager;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.in.service.PacketInServiceDisconnect;
import io.netty.channel.Channel;

public class HandlePacketInServiceDisconnect implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInServiceDisconnect){
            Group group = Driver.getInstance().getGroupDriver().load(CloudManager.serviceDriver.getService(((PacketInServiceDisconnect) packet).getService()).getEntry().getGroupName());
            if (group.getGroupType().equals("PROXY")){
                Driver.getInstance().getMessageStorage().eventDriver .executeEvent(new CloudProxyDisconnectedEvent(((PacketInServiceDisconnect) packet).getService()));
            }else {
                Driver.getInstance().getMessageStorage().eventDriver .executeEvent(new CloudServiceDisconnectedEvent(((PacketInServiceDisconnect) packet).getService()));

            }
            CloudManager.serviceDriver.unregister(((PacketInServiceDisconnect) packet).getService());
        }
    }
}
