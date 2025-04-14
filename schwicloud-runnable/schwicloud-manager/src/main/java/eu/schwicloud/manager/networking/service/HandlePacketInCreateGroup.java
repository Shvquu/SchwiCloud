/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.manager.networking.service;

import eu.schwicloud.Driver;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.groups.dummy.Group;
import eu.schwicloud.networking.packet.packets.in.service.cloudapi.PacketInCreateGroup;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketInCreateGroup implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInCreateGroup){
            Driver.getInstance().getGroupDriver().create((Group) new ConfigDriver().convert(((PacketInCreateGroup) packet).getGroupConfig(), Group.class));
        }
    }
}
