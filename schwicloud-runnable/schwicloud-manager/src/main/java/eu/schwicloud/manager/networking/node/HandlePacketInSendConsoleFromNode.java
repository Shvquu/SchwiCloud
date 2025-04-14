/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.manager.networking.node;

import eu.schwicloud.Driver;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.in.node.PacketInSendConsoleFromNode;
import io.netty.channel.Channel;

public class HandlePacketInSendConsoleFromNode implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInSendConsoleFromNode){
            Driver.getInstance().getTerminalDriver().log(Driver.getInstance().getMessageStorage().screenForm, ((PacketInSendConsoleFromNode) packet).getLine());
        }
    }
}
