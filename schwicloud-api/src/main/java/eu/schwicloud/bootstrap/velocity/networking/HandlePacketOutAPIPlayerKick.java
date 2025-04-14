package eu.schwicloud.bootstrap.velocity.networking;

import eu.schwicloud.bootstrap.velocity.VelocityBootstrap;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.apibased.PacketOutAPIPlayerKick;
import io.netty.channel.Channel;
import net.kyori.adventure.text.Component;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketOutAPIPlayerKick implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutAPIPlayerKick) {
            if (VelocityBootstrap.proxyServer.getPlayer(((PacketOutAPIPlayerKick) packet).getUsername()).isPresent()){
                VelocityBootstrap.proxyServer.getPlayer(((PacketOutAPIPlayerKick) packet).getUsername()).get().disconnect(Component.text(((PacketOutAPIPlayerKick) packet).getMessage()));
            }
        }
    }
}
