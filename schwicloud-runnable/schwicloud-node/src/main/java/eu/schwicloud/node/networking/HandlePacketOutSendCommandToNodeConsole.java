/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.node.networking;

import eu.schwicloud.Driver;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.node.PacketOutSendCommandToNodeConsole;
import io.netty.channel.Channel;

public class HandlePacketOutSendCommandToNodeConsole implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {

        if (packet instanceof PacketOutSendCommandToNodeConsole){
            Driver.getInstance().getTerminalDriver().getCommandDriver().executeCommand(((PacketOutSendCommandToNodeConsole) packet).getCommand());
        }
    }
}
