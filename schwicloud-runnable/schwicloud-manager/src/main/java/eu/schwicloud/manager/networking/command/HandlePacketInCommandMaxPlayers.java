/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.manager.networking.command;

import eu.schwicloud.Driver;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.groups.dummy.Group;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.in.service.command.PacketInCommandMaxPlayers;
import io.netty.channel.Channel;

public class HandlePacketInCommandMaxPlayers implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInCommandMaxPlayers){
            if (Driver.getInstance().getGroupDriver().find(((PacketInCommandMaxPlayers) packet).getGroup())){
                Group raw = Driver.getInstance().getGroupDriver().load(((PacketInCommandMaxPlayers) packet).getGroup());
                raw.setMaxPlayers(((PacketInCommandMaxPlayers) packet).getAmount());
                Driver.getInstance().getGroupDriver().update(((PacketInCommandMaxPlayers) packet).getGroup(), raw);
                Driver.getInstance().getWebServer().updateRoute("/cloudgroup/" + raw.getGroup(), new ConfigDriver().convert(raw));
            }

        }
    }
}
