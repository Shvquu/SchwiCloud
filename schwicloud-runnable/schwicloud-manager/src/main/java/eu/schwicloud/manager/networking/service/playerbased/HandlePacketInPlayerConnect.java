package eu.schwicloud.manager.networking.service.playerbased;

import eu.schwicloud.Driver;
import eu.schwicloud.cloudplayer.CloudPlayerRestCache;
import eu.schwicloud.cloudplayer.offlineplayer.ceched.OfflinePlayerCache;
import eu.schwicloud.cloudplayer.offlineplayer.ceched.OfflinePlayerCacheConfiguration;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.events.listeners.player.CloudPlayerConnectedEvent;
import eu.schwicloud.manager.CloudManager;
import eu.schwicloud.networking.NettyDriver;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.in.service.playerbased.PacketInPlayerConnect;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.PacketOutPlayerConnect;
import eu.schwicloud.storage.UUIDDriver;
import eu.schwicloud.terminal.enums.Type;
import eu.schwicloud.webserver.RestDriver;
import eu.schwicloud.webserver.dummys.PlayerGeneral;
import eu.schwicloud.webserver.entry.RouteEntry;
import io.netty.channel.Channel;

import java.util.Objects;

public class HandlePacketInPlayerConnect implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInPlayerConnect){
            if (!CloudManager.shutdown){
                String service = ((PacketInPlayerConnect) packet).getProxy();
                PlayerGeneral general = (PlayerGeneral) new ConfigDriver().convert(Driver.getInstance().getWebServer().getRoute("/cloudplayer/genernal"), PlayerGeneral.class);
                general.getCloudplayers().removeIf(s -> s.equalsIgnoreCase(UUIDDriver.getUUID(((PacketInPlayerConnect) packet).getName()).toString()));
                general.getCloudplayers().add(UUIDDriver.getUUID(((PacketInPlayerConnect) packet).getName()).toString());
                Driver.getInstance().getWebServer().updateRoute("/cloudplayer/genernal", new ConfigDriver().convert(general));
                CloudPlayerRestCache cache = new CloudPlayerRestCache(((PacketInPlayerConnect) packet).getName(), UUIDDriver.getUUID(((PacketInPlayerConnect) packet).getName()).toString());
                cache.handleConnect(service);
                cache.setCloudplayerservice("");
                Driver.getInstance().getWebServer().addRoute(new RouteEntry("/cloudplayer/" + UUIDDriver.getUUID(((PacketInPlayerConnect) packet).getName()), (new RestDriver()).convert(cache)));

                NettyDriver.getInstance().nettyServer.sendToAllSynchronized(new PacketOutPlayerConnect(((PacketInPlayerConnect) packet).getName(), service));
                Driver.getInstance().getMessageStorage().eventDriver.executeEvent(new CloudPlayerConnectedEvent(((PacketInPlayerConnect) packet).getName(), service, UUIDDriver.getUUID(((PacketInPlayerConnect) packet).getName())));


                if (CloudManager.config.isShowConnectingPlayers()){
                    Driver.getInstance().getTerminalDriver().log(Type.NETWORK, Driver.getInstance().getLanguageDriver().getLang().getMessage("network-player-connect").replace("%player%", ((PacketInPlayerConnect) packet).getName())
                            .replace("%uuid%", Objects.requireNonNull(UUIDDriver.getUUID(((PacketInPlayerConnect) packet).getName()).toString())).replace("%proxy%", ((PacketInPlayerConnect) packet).getProxy()));

                }
                CloudManager.serviceDriver.getService(service).handelCloudPlayerConnection(true);

                if (Driver.getInstance().getOfflinePlayerCacheDriver().readConfig().getPlayerCaches().stream().anyMatch(cp -> cp.getUniqueId().equals(UUIDDriver.getUUID(((PacketInPlayerConnect) packet).getName())))){
                    OfflinePlayerCacheConfiguration config = Driver.getInstance().getOfflinePlayerCacheDriver().readConfig();
                    OfflinePlayerCache offlinePlayerCache = config.getPlayerCaches().stream().filter(cache1 -> cache1.getUniqueId().equals(UUIDDriver.getUUID(((PacketInPlayerConnect) packet).getName()))).findFirst().orElse(null);
                    assert offlinePlayerCache != null;
                    offlinePlayerCache.setLastConnected("NOW");
                    offlinePlayerCache.setUniqueId(UUIDDriver.getUUID(((PacketInPlayerConnect) packet).getName()));
                    offlinePlayerCache.setConnectionCount(offlinePlayerCache.getConnectionCount() + 1);
                    offlinePlayerCache.setUsername(((PacketInPlayerConnect) packet).getName());
                    offlinePlayerCache.setLastProxy(((PacketInPlayerConnect) packet).getProxy());
                    config.getPlayerCaches().removeIf(c -> c.getUniqueId().equals(UUIDDriver.getUUID(((PacketInPlayerConnect) packet).getName())));
                    config.getPlayerCaches().add(offlinePlayerCache);
                    Driver.getInstance().getOfflinePlayerCacheDriver().saveConfig(config);
                }else {
                    OfflinePlayerCacheConfiguration config = Driver.getInstance().getOfflinePlayerCacheDriver().readConfig();
                    OfflinePlayerCache cache1 = new OfflinePlayerCache(((PacketInPlayerConnect) packet).getName(), UUIDDriver.getUUID(((PacketInPlayerConnect) packet).getName()), String.valueOf(System.currentTimeMillis()),  "NOW", ((PacketInPlayerConnect) packet).getProxy(), "",1, 0);

                    config.getPlayerCaches().add(cache1);
                    Driver.getInstance().getOfflinePlayerCacheDriver().saveConfig(config);
                }

               }
        }
    }
}
