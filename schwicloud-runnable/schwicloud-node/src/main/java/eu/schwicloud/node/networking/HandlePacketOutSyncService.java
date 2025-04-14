/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.node.networking;

import eu.schwicloud.Driver;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.node.PacketOutSyncService;
import eu.schwicloud.node.CloudNode;
import eu.schwicloud.terminal.enums.Type;
import io.netty.channel.Channel;

public class HandlePacketOutSyncService implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutSyncService){
            Driver.getInstance().getTerminalDriver().log(Type.NETWORK, Driver.getInstance().getLanguageDriver().getLang().getMessage("network-node-service-sync")
                    .replace("%service%", ((PacketOutSyncService) packet).getService()));
            CloudNode.cloudServiceDriver.sync(((PacketOutSyncService) packet).getService());
        }
    }
}
