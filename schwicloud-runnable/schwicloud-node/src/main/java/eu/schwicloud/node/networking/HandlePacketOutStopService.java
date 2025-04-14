package eu.schwicloud.node.networking;

import eu.schwicloud.Driver;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.node.PacketOutStopService;
import eu.schwicloud.node.CloudNode;
import eu.schwicloud.node.cloudservices.entry.QueueEntry;
import eu.schwicloud.terminal.enums.Type;
import io.netty.channel.Channel;

public class HandlePacketOutStopService implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {

        if (packet instanceof PacketOutStopService){
            Driver.getInstance().getTerminalDriver().log(Type.NETWORK, Driver.getInstance().getLanguageDriver().getLang().getMessage("network-node-service-stop")
                    .replace("%service%", ((PacketOutStopService) packet).getService()));
            CloudNode.cloudServiceDriver.addQueue(new QueueEntry(((PacketOutStopService) packet).getService()));
        }

    }
}
