package eu.schwicloud.node.networking;

import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.node.PacketOutEnableConsole;
import eu.schwicloud.node.CloudNode;
import io.netty.channel.Channel;

public class HandlePacketOutEnableConsole implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutEnableConsole){
            CloudNode.cloudServiceDriver.handleConsole(((PacketOutEnableConsole) packet).getService());
        }
    }
}
