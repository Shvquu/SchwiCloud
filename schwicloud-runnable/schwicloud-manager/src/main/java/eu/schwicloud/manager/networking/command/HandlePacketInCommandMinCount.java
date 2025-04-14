/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.manager.networking.command;

import eu.schwicloud.Driver;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.groups.dummy.Group;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.in.service.command.PacketInCommandMinCount;
import io.netty.channel.Channel;

public class HandlePacketInCommandMinCount implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInCommandMinCount){
            if (Driver.getInstance().getGroupDriver().find(((PacketInCommandMinCount) packet).getGroup())){
                Group raw = Driver.getInstance().getGroupDriver().load(((PacketInCommandMinCount) packet).getGroup());
                raw.setMinimalOnline(((PacketInCommandMinCount) packet).getAmount());
                Driver.getInstance().getGroupDriver().update(((PacketInCommandMinCount) packet).getGroup(), raw);
                Driver.getInstance().getWebServer().updateRoute("/cloudgroup/" + raw.getGroup(), new ConfigDriver().convert(raw));
            }
        }
    }
}
