package eu.schwicloud.bootstrap.velocity.networking;

import eu.schwicloud.bootstrap.velocity.VelocityBootstrap;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.apibased.PacketOutAPIPlayerMessage;
import io.netty.channel.Channel;
import net.kyori.adventure.text.Component;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketOutAPIPlayerMessage implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutAPIPlayerMessage) {
            if (VelocityBootstrap.proxyServer.getPlayer(((PacketOutAPIPlayerMessage) packet).getUsername()).isPresent()){
                VelocityBootstrap.proxyServer.getPlayer(((PacketOutAPIPlayerMessage) packet).getUsername()).get().sendMessage(Component.text(((PacketOutAPIPlayerMessage) packet).getMessage()));
            }
        }
    }
}
