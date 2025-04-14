package eu.schwicloud.node.networking;

import eu.schwicloud.Driver;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.terminal.enums.Type;
import io.netty.channel.Channel;

public class HandlePacketOutAuthSuccess implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        Driver.getInstance().getTerminalDriver().log(Type.NETWORK, Driver.getInstance().getLanguageDriver().getLang().getMessage("network-node-auth"));
    }
}
