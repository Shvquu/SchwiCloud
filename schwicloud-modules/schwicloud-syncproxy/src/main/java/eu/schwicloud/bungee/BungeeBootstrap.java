package eu.schwicloud.bungee;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.Driver;
import eu.schwicloud.bungee.listener.CloudEventHandler;
import eu.schwicloud.bungee.listener.MotdListener;
import eu.schwicloud.bungee.listener.TabListListener;
import eu.schwicloud.config.Configuration;
import eu.schwicloud.config.DesignConfig;
import eu.schwicloud.config.IconBase;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.configuration.dummys.serviceconfig.LiveService;
import eu.schwicloud.groups.dummy.Group;
import eu.schwicloud.moduleside.translate.Translator;
import eu.schwicloud.webserver.RestDriver;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BungeeBootstrap extends Plugin {


    private static BungeeBootstrap instance;

    public Translator translator;
    public BungeeAudiences bungeeAudiences;
    private LiveService liveService;
    private RestDriver restDriver;
    public Integer motdCount;
    public DesignConfig configuration;
    public Configuration conf;

    public IconBase iconBase;

    public  Group group;
    public Integer tabCount;

    @Override
    public void onEnable() {
        new Driver();
        tabCount = 0;
        motdCount = 0;
        instance = this;

        liveService = (LiveService) new ConfigDriver("./CLOUDSERVICE.json").read(LiveService.class);
        restDriver = new RestDriver(liveService.getManagerAddress(), liveService.getRestPort());

        CloudAPI.getInstance().getEventDriver().registerListener(new CloudEventHandler());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new MotdListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new TabListListener());
        translator = new Translator();
        bungeeAudiences = BungeeAudiences.create(this);

        updater();

    }

    @Override
    public void onDisable() {
    }


    public RestDriver getRestDriver() {
        return restDriver;
    }

    public static BungeeBootstrap getInstance() {
        return instance;
    }

    public LiveService getLiveService() {
        return liveService;
    }

    private void  updater() {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            conf = (Configuration) new ConfigDriver().convert(getRestDriver().get("/module/syncproxy/configuration"), Configuration.class);
            iconBase = (IconBase) new ConfigDriver().convert(getRestDriver().get("/module/syncproxy/icons"), IconBase.class);
            conf.getConfiguration().stream()
                    .filter(designConfig -> designConfig.getTargetGroup().equalsIgnoreCase(liveService.getGroup()))
                    .findFirst()
                    .ifPresent(config -> {

                        configuration = config;
                        group = CloudAPI.getInstance().getGroupPool().getGroup(getLiveService().getGroup());
                        tabCount = tabCount >= configuration.getTablist().size() - 1 ? 0 : tabCount + 1;
                        if (group.isMaintenance()) {
                            motdCount = motdCount >= configuration.getMaintenancen().size() - 1 ? 0 : motdCount + 1;
                        } else {
                            motdCount = motdCount >= configuration.getDefaults().size() - 1 ? 0 : motdCount + 1;
                        }
                    });
        }, 0, 1, TimeUnit.SECONDS);
    }
}
