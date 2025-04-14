package eu.schwicloud.bootstrap.bungee.networking;

import eu.schwicloud.bootstrap.bungee.BungeeBootstrap;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.apibased.PacketOutCloudPlayerComponent;
import io.netty.channel.Channel;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketOutCloudPlayerComponent implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutCloudPlayerComponent){
            if (ProxyServer.getInstance().getPlayer(((PacketOutCloudPlayerComponent) packet).getPlayer()).isConnected()){
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(((PacketOutCloudPlayerComponent) packet).getPlayer());
                BungeeBootstrap.getInstance().getAudiences().player(player).sendMessage(((PacketOutCloudPlayerComponent) packet).getComponent());
            }
        }
    }
}
