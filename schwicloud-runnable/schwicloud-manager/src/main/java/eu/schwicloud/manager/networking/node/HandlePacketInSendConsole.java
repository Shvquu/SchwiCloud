package eu.schwicloud.manager.networking.node;

import eu.schwicloud.Driver;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.in.node.PacketInSendConsole;
import io.netty.channel.Channel;

public class HandlePacketInSendConsole implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInSendConsole){
            Driver.getInstance().getTerminalDriver().log(((PacketInSendConsole) packet).getService(), ((PacketInSendConsole) packet).getLine());
        }
    }
}
