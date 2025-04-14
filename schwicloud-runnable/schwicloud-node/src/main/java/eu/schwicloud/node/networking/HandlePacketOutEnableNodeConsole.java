/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.node.networking;

import eu.schwicloud.Driver;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.node.PacketOutEnableNodeConsole;
import io.netty.channel.Channel;

public class HandlePacketOutEnableNodeConsole implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutEnableNodeConsole){
            Driver.getInstance().getMessageStorage().sendConsoleToManager = true;

        }
    }
}
