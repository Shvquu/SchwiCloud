package eu.schwicloud.bootstrap.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import eu.schwicloud.CloudAPI;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.configuration.dummys.serviceconfig.LiveService;
import eu.schwicloud.networking.NettyDriver;
import eu.schwicloud.networking.packet.packets.in.service.PacketInServiceDisconnect;

import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

@Plugin(id = "metacloudapi", name = "metacloud-api", version = "1.1.0-RELEASE", authors = "RauchigesEtwas")
public class VelocityBootstrap {

    public static ProxyServer proxyServer;
    @Inject(optional = false)
    public VelocityBootstrap(ProxyServer proxyServer, Logger logger) {
        VelocityBootstrap.proxyServer = proxyServer;
        new CloudAPI(true);
    }

    @Subscribe
    public void onProxyInject(ProxyInitializeEvent event){}

    @Subscribe
    public void onDisable(ProxyShutdownEvent event){
        proxyServer.getAllPlayers().forEach(player -> {
            player.disconnect(Component.text("cloudservice-shutdown"));
        });
        LiveService service = (LiveService) new ConfigDriver("./CLOUDSERVICE.json").read(LiveService.class);
        NettyDriver.getInstance().nettyClient.sendPacketSynchronized(new PacketInServiceDisconnect(service.getService()));
    }

}
