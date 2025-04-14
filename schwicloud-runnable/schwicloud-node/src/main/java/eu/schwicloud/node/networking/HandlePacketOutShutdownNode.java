package eu.schwicloud.node.networking;

import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.node.PacketOutShutdownNode;
import io.netty.channel.Channel;

public class HandlePacketOutShutdownNode implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutShutdownNode){
            System.exit(0);
        }
    }

}
