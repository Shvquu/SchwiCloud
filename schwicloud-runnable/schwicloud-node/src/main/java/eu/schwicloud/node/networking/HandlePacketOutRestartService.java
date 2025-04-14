package eu.schwicloud.node.networking;

import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.node.PacketOutRestartService;
import eu.schwicloud.node.CloudNode;
import io.netty.channel.Channel;

public class HandlePacketOutRestartService implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutRestartService){
            CloudNode.cloudServiceDriver.handleRestart(((PacketOutRestartService) packet).getService());
        }
    }
}
