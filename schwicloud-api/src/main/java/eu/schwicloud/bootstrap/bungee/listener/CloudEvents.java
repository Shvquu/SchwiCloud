package eu.schwicloud.bootstrap.bungee.listener;


import eu.schwicloud.bootstrap.bungee.utils.ServerDriver;
import eu.schwicloud.events.entrys.ICloudListener;
import eu.schwicloud.events.entrys.Priority;
import eu.schwicloud.events.entrys.Subscribe;
import eu.schwicloud.events.listeners.services.CloudServiceConnectedEvent;
import eu.schwicloud.events.listeners.services.CloudServiceDisconnectedEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.net.InetSocketAddress;

public class CloudEvents implements ICloudListener {


    @Subscribe(priority = Priority.HIGHEST)
    public void handle(CloudServiceConnectedEvent event){
        ServerInfo info = ProxyServer.getInstance().constructServerInfo(event.getName(), new InetSocketAddress(event.getHost(), event.getPort()), "metacloud-service", false);
        new ServerDriver().addServer(info);

    }

    @Subscribe(priority = Priority.HIGHEST)
    public void handle(CloudServiceDisconnectedEvent event){
        new ServerDriver().removeServer(event.getName());
    }

}
