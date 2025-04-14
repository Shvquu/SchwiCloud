package eu.schwicloud.node.networking;

import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.node.PacketOutSendCommand;
import eu.schwicloud.node.CloudNode;
import io.netty.channel.Channel;

public class HandlePacketOutSendCommand implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutSendCommand){
            CloudNode.cloudServiceDriver.execute(((PacketOutSendCommand)packet).getService(), ((PacketOutSendCommand)packet).getCommand());
        }
    }
}
