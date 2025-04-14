/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.node.networking;

import eu.schwicloud.Driver;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.node.PacketOutDisableNodeConsole;
import io.netty.channel.Channel;

public class HandlePacketOutDisableNodeConsole implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutDisableNodeConsole){
            Driver.getInstance().getMessageStorage().sendConsoleToManager = false;
        }
    }
}
