package eu.schwicloud.node.networking;

import eu.schwicloud.Driver;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.node.PacketOutLaunchService;
import eu.schwicloud.node.CloudNode;
import eu.schwicloud.node.cloudservices.entry.QueueEntry;
import eu.schwicloud.terminal.enums.Type;
import io.netty.channel.Channel;

public class HandlePacketOutLaunchService implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {

        if (packet instanceof PacketOutLaunchService){
            Driver.getInstance().getTerminalDriver().log(Type.NETWORK, Driver.getInstance().getLanguageDriver().getLang().getMessage("network-node-service-launch")
                    .replace("%service%", ((PacketOutLaunchService) packet).getService()));
            CloudNode.cloudServiceDriver.addQueue(new QueueEntry(((PacketOutLaunchService) packet).getService(),
                    ((PacketOutLaunchService) packet).getGroup(),
                    ((PacketOutLaunchService) packet).isUseProtocol()));
        }


    }
}
