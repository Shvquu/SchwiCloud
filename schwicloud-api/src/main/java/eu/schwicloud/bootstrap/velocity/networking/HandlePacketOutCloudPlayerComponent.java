package eu.schwicloud.bootstrap.velocity.networking;

import com.velocitypowered.api.proxy.Player;
import eu.schwicloud.bootstrap.velocity.VelocityBootstrap;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.apibased.PacketOutCloudPlayerComponent;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketOutCloudPlayerComponent implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutCloudPlayerComponent){
            if (VelocityBootstrap.proxyServer.getPlayer(((PacketOutCloudPlayerComponent) packet).getPlayer()).isPresent()){
                Player player =   VelocityBootstrap.proxyServer.getPlayer(((PacketOutCloudPlayerComponent) packet).getPlayer()).get();
                player.sendMessage(((PacketOutCloudPlayerComponent) packet).getComponent());
            }
        }
    }
}
