package eu.schwicloud.bootstrap.velocity.networking;

import eu.schwicloud.bootstrap.velocity.VelocityBootstrap;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.apibased.PacketOutAPIPlayerTab;
import io.netty.channel.Channel;
import net.kyori.adventure.text.Component;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketOutAPIPlayerTab implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutAPIPlayerTab){
            if (VelocityBootstrap.proxyServer.getPlayer(((PacketOutAPIPlayerTab) packet).getUsername()).isPresent()){
                VelocityBootstrap.proxyServer.getPlayer(((PacketOutAPIPlayerTab) packet).getUsername()).get().getTabList().setHeaderAndFooter(Component.text(((PacketOutAPIPlayerTab) packet).getHeader()), Component.text(((PacketOutAPIPlayerTab) packet).getFooter()));
            }
        }

    }
}
