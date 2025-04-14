package eu.schwicloud.bootstrap.bungee.networking;

import eu.schwicloud.networking.packet.packets.out.service.playerbased.apibased.PacketOutAPIPlayerActionBar;
import io.netty.channel.Channel;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketOutAPIPlayerActionBar implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutAPIPlayerActionBar){
            if (ProxyServer.getInstance().getPlayer(((PacketOutAPIPlayerActionBar) packet).getUsername()).isConnected()){
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(((PacketOutAPIPlayerActionBar) packet).getUsername());
                player.sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(((PacketOutAPIPlayerActionBar) packet).getMessage()));
            }
        }
    }
}
