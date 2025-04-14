package eu.schwicloud.bootstrap.velocity.listener;


import eu.schwicloud.bootstrap.velocity.VelocityBootstrap;
import eu.schwicloud.events.entrys.ICloudListener;
import eu.schwicloud.events.entrys.Priority;
import eu.schwicloud.events.entrys.Subscribe;
import eu.schwicloud.events.listeners.services.CloudServiceConnectedEvent;
import eu.schwicloud.events.listeners.services.CloudServiceDisconnectedEvent;

import java.net.InetSocketAddress;

public class CloudEvents implements ICloudListener {


    @Subscribe(priority = Priority.HIGHEST)
    public void handle(CloudServiceConnectedEvent event){
        VelocityBootstrap.proxyServer.registerServer(new com.velocitypowered.api.proxy.server.ServerInfo(event.getName(), new InetSocketAddress(event.getHost(), event.getPort())));
    }

    @Subscribe
    public void handle(CloudServiceDisconnectedEvent event){
        if (!VelocityBootstrap.proxyServer.getServer(event.getName()).isPresent()) return;
        VelocityBootstrap.proxyServer.unregisterServer(VelocityBootstrap.proxyServer.getServer(event.getName()).get().getServerInfo());
    }

}
