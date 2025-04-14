package eu.schwicloud.bungee.listener;

import eu.schwicloud.bungee.BungeeBootstrap;
import eu.schwicloud.config.Configuration;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.events.entrys.ICloudListener;
import eu.schwicloud.events.entrys.Subscribe;
import eu.schwicloud.events.listeners.services.*;
import net.md_5.bungee.api.ProxyServer;

public class CloudListener implements ICloudListener {


    @Subscribe
    public void handle(CloudProxyDisconnectedEvent event){

        Configuration configuration = (Configuration) new ConfigDriver().convert(BungeeBootstrap.getInstance().getRestDriver().get("/module/notify/configuration"), Configuration.class);

        ProxyServer.getInstance().getPlayers().forEach(player -> {
            if (player.hasPermission("metacloud.notify")){
                player.sendMessage(configuration.getProxiedServiceDiconnected().replace("&", "§").replace("%service_name%", event.getName()));
            }
        });
    }

    @Subscribe
    public void handle(CloudProxyConnectedEvent event){
        Configuration configuration = (Configuration) new ConfigDriver().convert(BungeeBootstrap.getInstance().getRestDriver().get("/module/notify/configuration"), Configuration.class);

        ProxyServer.getInstance().getPlayers().forEach(player -> {
            if (player.hasPermission("metacloud.notify")){
                player.sendMessage((configuration.getProxiedServiceConnected()).replace("&", "§")
                        .replace("%service_name%", event.getName()).replace("%service_node%", event.getNode()));
            }
        });
    }

    @Subscribe
    public void handle(CloudProxyPreparedEvent event){
        Configuration configuration = (Configuration) new ConfigDriver().convert(BungeeBootstrap.getInstance().getRestDriver().get("/module/notify/configuration"), Configuration.class);

        ProxyServer.getInstance().getPlayers().forEach(player -> {
            if (player.hasPermission("metacloud.notify")){
                player.sendMessage((configuration.getProxiedServicePrepared()).replace("&", "§")
                        .replace("%service_name%", event.getName()).replace("%service_node%", event.getNode()));
            }
        });
    }

    @Subscribe
    public void handle(CloudServiceConnectedEvent event){
        Configuration configuration = (Configuration) new ConfigDriver().convert(BungeeBootstrap.getInstance().getRestDriver().get("/module/notify/configuration"), Configuration.class);

        ProxyServer.getInstance().getPlayers().forEach(player -> {
            if (player.hasPermission("metacloud.notify")) {
                player.sendMessage((configuration.getServiceConnected()).replace("&", "§")
                        .replace("%service_name%", event.getName()).replace("%service_node%", event.getNode()));
            }
        });
    }

    @Subscribe
    public void handle(CloudServiceDisconnectedEvent event){
        Configuration configuration = (Configuration) new ConfigDriver().convert(BungeeBootstrap.getInstance().getRestDriver().get("/module/notify/configuration"), Configuration.class);

        ProxyServer.getInstance().getPlayers().forEach(player -> {

            if (player.hasPermission("metacloud.notify")){
                player.sendMessage((configuration.getServiceDiconnected()).replace("&", "§")
                        .replace("%service_name%", event.getName()));

            }
        });
    }

    @Subscribe
    public void handle(CloudServicePreparedEvent event){
        Configuration configuration = (Configuration) new ConfigDriver().convert(BungeeBootstrap.getInstance().getRestDriver().get("/module/notify/configuration"), Configuration.class);

        ProxyServer.getInstance().getPlayers().forEach(player -> {
            if (player.hasPermission("metacloud.notify")){
                player.sendMessage((configuration.getServicePrepared()).replace("&", "§")
                        .replace("%service_name%", event.getName()).replace("%service_node%", event.getNode()));
            }

        });
    }
    @Subscribe
    public void handle(CloudProxyLaunchEvent event){
        Configuration configuration = (Configuration) new ConfigDriver().convert(BungeeBootstrap.getInstance().getRestDriver().get("/module/notify/configuration"), Configuration.class);

        ProxyServer.getInstance().getPlayers().forEach(player -> {
            if (player.hasPermission("metacloud.notify")){
                player.sendMessage((configuration.getProxiedServiceLaunch()).replace("&", "§")
                        .replace("%service_name%", event.getName()).replace("%service_node%", event.getNode()));
            }

        });
    }
    @Subscribe
    public void handle(CloudServiceLaunchEvent event){
        Configuration configuration = (Configuration) new ConfigDriver().convert(BungeeBootstrap.getInstance().getRestDriver().get("/module/notify/configuration"), Configuration.class);

        ProxyServer.getInstance().getPlayers().forEach(player -> {
            if (player.hasPermission("metacloud.notify")){
                player.sendMessage((configuration.getServiceLaunch()).replace("&", "§")
                        .replace("%service_name%", event.getName()).replace("%service_node%", event.getNode()));
            }

        });
    }
}
