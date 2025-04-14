package eu.schwicloud.node.networking;

import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.node.PacketOutDisableConsole;
import eu.schwicloud.node.CloudNode;
import io.netty.channel.Channel;

public class HandlePacketOutDisableConsole implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutDisableConsole){
            CloudNode.cloudServiceDriver.handleConsole(((PacketOutDisableConsole) packet).getService());
        }
    }
}
