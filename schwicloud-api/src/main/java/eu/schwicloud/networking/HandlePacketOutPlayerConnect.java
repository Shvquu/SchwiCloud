package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.player.async.entrys.AsyncCloudPlayer;
import eu.schwicloud.events.listeners.player.CloudPlayerConnectedEvent;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.PacketOutPlayerConnect;
import eu.schwicloud.player.entrys.CloudPlayer;
import eu.schwicloud.storage.UUIDDriver;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;

import java.util.UUID;

public class HandlePacketOutPlayerConnect implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutPlayerConnect){
            if (!CloudAPI.getInstance().getPlayerPool().playerIsNotNull(((PacketOutPlayerConnect) packet).getName())){

                UUID uniqueId = UUIDDriver.getUUID(((PacketOutPlayerConnect) packet).getName());

                CloudAPI.getInstance().getAsyncPlayerPool().registerPlayer(new AsyncCloudPlayer(((PacketOutPlayerConnect) packet).getName(),
                        uniqueId));

                CloudAPI.getInstance().getPlayerPool().registerPlayer(new CloudPlayer(((PacketOutPlayerConnect) packet).getName(),
                        uniqueId));


                CloudAPI.getInstance().getEventDriver().executeEvent(new CloudPlayerConnectedEvent(((PacketOutPlayerConnect) packet).getName(),
                        ((PacketOutPlayerConnect) packet).getProxy(), UUIDDriver.getUUID(((PacketOutPlayerConnect) packet).getName())));
            }

         }
    }
}
