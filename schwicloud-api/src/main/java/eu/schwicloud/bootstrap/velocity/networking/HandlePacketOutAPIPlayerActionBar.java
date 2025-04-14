package eu.schwicloud.bootstrap.velocity.networking;

import eu.schwicloud.bootstrap.velocity.VelocityBootstrap;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.apibased.PacketOutAPIPlayerActionBar;
import io.netty.channel.Channel;
import net.kyori.adventure.text.Component;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketOutAPIPlayerActionBar implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutAPIPlayerActionBar){
            if (VelocityBootstrap.proxyServer.getPlayer(((PacketOutAPIPlayerActionBar) packet).getUsername()).isEmpty()) return;
            VelocityBootstrap.proxyServer.getPlayer(((PacketOutAPIPlayerActionBar) packet).getUsername()).get().sendActionBar(Component.text(((PacketOutAPIPlayerActionBar) packet).getMessage()));
        }
    }
}
