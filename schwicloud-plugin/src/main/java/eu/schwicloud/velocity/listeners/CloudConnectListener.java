package eu.schwicloud.velocity.listeners;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.KickedFromServerEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import eu.schwicloud.CloudAPI;
import eu.schwicloud.api.translate.Translator;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.configuration.dummys.serviceconfig.LiveService;
import eu.schwicloud.groups.dummy.Group;
import eu.schwicloud.networking.packet.packets.in.service.playerbased.PacketInPlayerConnect;
import eu.schwicloud.networking.packet.packets.in.service.playerbased.PacketInPlayerDisconnect;
import eu.schwicloud.networking.packet.packets.in.service.playerbased.PacketInPlayerSwitchService;
import eu.schwicloud.service.entrys.CloudService;
import eu.schwicloud.velocity.VelocityBootstrap;

import java.util.ArrayList;
import java.util.UUID;

public class CloudConnectListener {


    private final ArrayList<UUID> connected = new ArrayList<>();
    private final ProxyServer server;
    public ServerInfo target;

    public CloudConnectListener(ProxyServer server) {
        this.server = server;
    }


    @Subscribe(order = PostOrder.FIRST)
    public void handel(ServerPreConnectEvent event){
        if (event.getOriginalServer().getServerInfo().getName().equalsIgnoreCase("lobby")){
            target = server.getServer(VelocityBootstrap.getLobby(event.getPlayer()).getName()).get().getServerInfo();
            if (target != null){
                event.setResult(ServerPreConnectEvent.ServerResult.allowed(server.getServer(target.getName()).get()));
            }else event.setResult(ServerPreConnectEvent.ServerResult.denied());
        }else if (event.getOriginalServer() == null){
            target = server.getServer(VelocityBootstrap.getLobby(event.getPlayer()).getName()).get().getServerInfo();
            if (target != null){
                event.setResult(ServerPreConnectEvent.ServerResult.allowed(server.getServer(target.getName()).get()));
            }else event.setResult(ServerPreConnectEvent.ServerResult.denied());
        }
    }



    @Subscribe
    public void handle(PostLoginEvent event){
        LiveService service = (LiveService)(new ConfigDriver("./CLOUDSERVICE.json")).read(LiveService.class);
        Group group = CloudAPI.getInstance().getGroupPool().getGroup(service.getGroup());

        if (CloudAPI.getInstance().getPlayerPool().getPlayers().stream().anyMatch(cloudPlayer -> cloudPlayer.getUsername().equalsIgnoreCase(event.getPlayer().getUsername()))){
            event.getPlayer().disconnect(VelocityBootstrap.message.deserialize(new Translator().translate(CloudAPI.getInstance().getMessages().getMessages().get("kickAlreadyOnNetwork"))));
        }
        this.connected.add(event.getPlayer().getUniqueId());
        CloudAPI.getInstance().sendPacketSynchronized(new PacketInPlayerConnect(event.getPlayer().getUsername(), service.getService()));

        if (group.isMaintenance()) {
            if (!server.getPlayer(event.getPlayer().getUniqueId()).get().hasPermission("metacloud.connection.maintenance")
                    && !CloudAPI.getInstance().getWhitelist().contains(server.getPlayer(event.getPlayer().getUniqueId()).get().getUsername())){
                event.getPlayer().disconnect(VelocityBootstrap.message.deserialize(new Translator().translate(CloudAPI.getInstance().getMessages().getMessages().get("kickNetworkIsMaintenance"))));
            }
        }else {
            if (CloudAPI.getInstance().getPlayerPool().getPlayers().size() >= group.getMaxPlayers()
                    && !server.getPlayer(event.getPlayer().getUniqueId()).get().hasPermission("metacloud.connection.full")
                    && !CloudAPI.getInstance().getWhitelist().contains(server.getPlayer(event.getPlayer().getUniqueId()).get().getUsername())){
                event.getPlayer().disconnect(VelocityBootstrap.message.deserialize(new Translator().translate(CloudAPI.getInstance().getMessages().getMessages().get("kickNetworkIsFull"))));


            }else if (server.getPlayer(event.getPlayer().getUniqueId()).isPresent()
                    && VelocityBootstrap.getLobby( server.getPlayer(event.getPlayer().getUniqueId()).get()) == null){

                event.getPlayer().disconnect(VelocityBootstrap.message.deserialize(new Translator().translate(CloudAPI.getInstance().getMessages().getMessages().get("kickNoFallback"))));

            }
        }
    }

    @Subscribe
    public void handle(DisconnectEvent event){
        if (this.connected.contains(event.getPlayer().getUniqueId())) {
            CloudAPI.getInstance().sendPacketSynchronized(new PacketInPlayerDisconnect(event.getPlayer().getUsername()));
        }
    }

    @Subscribe
    public void handle(ServerConnectedEvent event){
        CloudAPI.getInstance().sendPacketSynchronized(new PacketInPlayerSwitchService(event.getPlayer().getUsername(), event.getServer().getServerInfo().getName()));
    }

    @Subscribe
    public void handle(KickedFromServerEvent event){
        if (event.getPlayer().isActive()) {
            CloudService target = VelocityBootstrap.getLobby(event.getPlayer(), event.getServer().getServerInfo().getName());
            if (target != null) {
                event.setResult(KickedFromServerEvent.RedirectPlayer.create(server.getServer(target.getName()).get()));
            } else {
                event.setResult(KickedFromServerEvent.DisconnectPlayer.create(VelocityBootstrap.message.deserialize(new Translator().translate(CloudAPI.getInstance().getMessages().getMessages().get("kickNoFallback")))));

            }
        }
    }


}
