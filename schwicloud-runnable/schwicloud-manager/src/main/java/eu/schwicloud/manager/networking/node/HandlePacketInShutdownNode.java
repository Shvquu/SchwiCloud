package eu.schwicloud.manager.networking.node;

import eu.schwicloud.Driver;
import eu.schwicloud.manager.CloudManager;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.in.node.PacketInShutdownNode;
import eu.schwicloud.networking.NettyDriver;
import eu.schwicloud.terminal.enums.Type;
import io.netty.channel.Channel;

public class HandlePacketInShutdownNode implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {

        if (packet instanceof PacketInShutdownNode){
            CloudManager.serviceDriver.getServicesFromNode(((PacketInShutdownNode) packet).getNode()).forEach(taskedService -> {
                CloudManager.serviceDriver.unregister(taskedService.getEntry().getServiceName());
            });
            if (Driver.getInstance().getMessageStorage().screenForm.equalsIgnoreCase(((PacketInShutdownNode) packet).getNode())){
                CloudManager.screenNode(((PacketInShutdownNode) packet).getNode());
            }
            NettyDriver.getInstance().nettyServer.removeChannel(((PacketInShutdownNode) packet).getNode());
            Driver.getInstance().getTerminalDriver().log(Type.INFO, Driver.getInstance().getLanguageDriver().getLang().getMessage("network-node-stop")
                    .replace("%node%", ((PacketInShutdownNode) packet).getNode()));

        }

    }
}
