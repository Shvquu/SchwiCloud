package eu.schwicloud.bungee;

import eu.schwicloud.bungee.listener.CloudListener;
import eu.schwicloud.CloudAPI;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.configuration.dummys.serviceconfig.LiveService;
import eu.schwicloud.webserver.RestDriver;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeBootstrap extends Plugin {

    private static  BungeeBootstrap instance;
    private LiveService liveService;
    private RestDriver restDriver;

    @Override
    public void onEnable() {
        instance = this;
        liveService = (LiveService) new ConfigDriver("./CLOUDSERVICE.json").read(LiveService.class);
        restDriver = new RestDriver(liveService.getManagerAddress(), liveService.getRestPort());
        CloudAPI.getInstance().registerListener(new CloudListener());
    }

    public RestDriver getRestDriver() {
        return restDriver;
    }

    public static BungeeBootstrap getInstance() {
        return instance;
    }
}
