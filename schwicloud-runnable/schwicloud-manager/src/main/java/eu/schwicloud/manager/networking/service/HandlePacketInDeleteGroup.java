/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.manager.networking.service;

import eu.schwicloud.Driver;
import eu.schwicloud.manager.CloudManager;
import eu.schwicloud.networking.packet.packets.in.service.cloudapi.PacketInDeleteGroup;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketInDeleteGroup implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInDeleteGroup){
            Driver.getInstance().getGroupDriver().delete(((PacketInDeleteGroup) packet).getGroup());
            CloudManager.serviceDriver.delete.add(((PacketInDeleteGroup) packet).getGroup());
            CloudManager.serviceDriver.getServices(((PacketInDeleteGroup) packet).getGroup()).forEach(taskedService -> CloudManager.serviceDriver.unregister(taskedService.getEntry().getServiceName()));
        }
    }
}
