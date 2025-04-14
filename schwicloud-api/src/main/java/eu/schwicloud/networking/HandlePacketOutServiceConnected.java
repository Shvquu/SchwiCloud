package eu.schwicloud.networking;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.service.async.entrys.AsyncCloudService;
import eu.schwicloud.events.listeners.services.CloudProxyConnectedEvent;
import eu.schwicloud.events.listeners.services.CloudServiceConnectedEvent;
import eu.schwicloud.networking.packet.packets.out.service.PacketOutServiceConnected;
import eu.schwicloud.service.entrys.CloudService;
import io.netty.channel.Channel;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
public class HandlePacketOutServiceConnected implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketOutServiceConnected){
            if (!CloudAPI.getInstance().getServicePool().serviceNotNull(((PacketOutServiceConnected) packet).getName())){
                CloudAPI.getInstance().getServicePool().registerService(new CloudService(((PacketOutServiceConnected) packet).getName(), ((PacketOutServiceConnected) packet).getGroup()));
                CloudAPI.getInstance().getAsyncServicePool().registerService(new AsyncCloudService(((PacketOutServiceConnected) packet).getName(), ((PacketOutServiceConnected) packet).getGroup()));
                CloudService cloudService = CloudAPI.getInstance().getServicePool().getService(((PacketOutServiceConnected) packet).getName());
                if (cloudService.getGroup().getGroupType().equals("PROXY")){
                    try {
                        CloudAPI.getInstance().getEventDriver().executeEvent(new CloudProxyConnectedEvent(cloudService.getName(), cloudService.getGroup().getStorage().getRunningNode(), cloudService.getPort(), cloudService.getAddress(), cloudService.getGroup().getGroup()));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    try {
                        CloudAPI.getInstance().getEventDriver().executeEvent(new CloudServiceConnectedEvent(cloudService.getName(), cloudService.getGroup().getStorage().getRunningNode(), cloudService.getPort(), cloudService.getAddress(), cloudService.getGroup().getGroup()));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
