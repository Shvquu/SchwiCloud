package eu.schwicloud.bootstrap.bungee.networking;

import eu.schwicloud.networking.packet.packets.out.service.playerbased.apibased.PacketOutAPIPlayerConnect;
import io.netty.channel.Channel;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketOutAPIPlayerConnect implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutAPIPlayerConnect) {
            if (ProxyServer.getInstance().getPlayer(((PacketOutAPIPlayerConnect) packet).getUsername()).isConnected()){
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(((PacketOutAPIPlayerConnect) packet).getUsername());
                player.connect(ProxyServer.getInstance().getServerInfo(((PacketOutAPIPlayerConnect) packet).getService()));
            }
        }
    }
}
