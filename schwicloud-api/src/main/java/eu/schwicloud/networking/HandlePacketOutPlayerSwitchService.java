package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.player.async.entrys.AsyncCloudPlayer;
import eu.schwicloud.events.listeners.player.CloudPlayerConnectedEvent;
import eu.schwicloud.events.listeners.player.CloudPlayerSwitchEvent;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.PacketOutPlayerSwitchService;
import eu.schwicloud.player.entrys.CloudPlayer;
import eu.schwicloud.storage.UUIDDriver;
import io.netty.channel.Channel;

public class HandlePacketOutPlayerSwitchService implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutPlayerSwitchService){

            if (!CloudAPI.getInstance().getPlayerPool().playerIsNotNull(((PacketOutPlayerSwitchService) packet).getName())){
                CloudAPI.getInstance().getAsyncPlayerPool().registerPlayer(new AsyncCloudPlayer(((PacketOutPlayerSwitchService) packet).getName(), UUIDDriver.getUUID(((PacketOutPlayerSwitchService) packet).getName())));
                CloudAPI.getInstance().getPlayerPool().registerPlayer(new CloudPlayer(((PacketOutPlayerSwitchService) packet).getName(), UUIDDriver.getUUID(((PacketOutPlayerSwitchService) packet).getName())));
                CloudAPI.getInstance().getEventDriver().executeEvent(new CloudPlayerConnectedEvent(((PacketOutPlayerSwitchService) packet).getName(), CloudAPI.getInstance().getPlayerPool().getPlayer(((PacketOutPlayerSwitchService) packet).getName()).getProxyServer().getName(), UUIDDriver.getUUID(((PacketOutPlayerSwitchService) packet).getName())));
            }

            CloudAPI.getInstance().getEventDriver().executeEvent(new CloudPlayerSwitchEvent(((PacketOutPlayerSwitchService) packet).getName(), UUIDDriver.getUUID(((PacketOutPlayerSwitchService) packet).getName()), ((PacketOutPlayerSwitchService) packet).getFrom(), ((PacketOutPlayerSwitchService) packet).getServer()));
        }
    }
}
