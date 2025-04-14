package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.events.listeners.player.CloudPlayerDisconnectedEvent;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.PacketOutPlayerDisconnect;
import eu.schwicloud.storage.UUIDDriver;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;

public class HandlePacketOutPlayerDisconnect implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutPlayerDisconnect){
            if (CloudAPI.getInstance().getPlayerPool().playerIsNotNull(((PacketOutPlayerDisconnect) packet).getName())){
                CloudAPI.getInstance().getPlayerPool().unregisterPlayer(((PacketOutPlayerDisconnect) packet).getName());
                CloudAPI.getInstance().getAsyncPlayerPool().unregisterPlayer(((PacketOutPlayerDisconnect) packet).getName());
                CloudAPI.getInstance().getEventDriver().executeEvent(new CloudPlayerDisconnectedEvent(((PacketOutPlayerDisconnect) packet).getName(), UUIDDriver.getUUID(((PacketOutPlayerDisconnect) packet).getName())));
            }
        }
    }
}
