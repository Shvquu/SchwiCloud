package eu.schwicloud.manager.networking.service;

import eu.schwicloud.Driver;
import eu.schwicloud.networking.packet.packets.in.service.cloudapi.PacketInDispatchMainCommand;
import io.netty.channel.Channel;import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;

public class HandlePacketInDispatchMainCommand implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInDispatchMainCommand){
            Driver.getInstance().getTerminalDriver().getCommandDriver().executeCommand(((PacketInDispatchMainCommand) packet).getCommand());
        }
    }
}
