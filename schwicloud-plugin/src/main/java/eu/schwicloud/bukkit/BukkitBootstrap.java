package eu.schwicloud.bukkit;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.Driver;
import eu.schwicloud.api.PluginDriver;
import eu.schwicloud.bukkit.command.impli.InformationCommand;
import eu.schwicloud.bukkit.command.ServiceCommand;
import eu.schwicloud.bukkit.command.impli.ShutdownCommand;
import eu.schwicloud.bukkit.listener.ReloadBlocker;
import eu.schwicloud.bukkit.listener.ServiceConnectListener;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.configuration.dummys.serviceconfig.LiveService;
import eu.schwicloud.networking.NettyDriver;
import eu.schwicloud.process.ServiceState;
import eu.schwicloud.timebaser.TimerBase;
import eu.schwicloud.timebaser.utils.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.TimerTask;

public class BukkitBootstrap extends JavaPlugin {

    public static  LiveService service;

    @Override
    public void onEnable(){
        new Driver();
        new PluginDriver();
         service = (LiveService) new ConfigDriver("./CLOUDSERVICE.json").read(LiveService.class);
        CloudAPI.getInstance().setState(ServiceState.LOBBY, service.getService());
        Bukkit.getPluginManager().registerEvents(new ReloadBlocker(), this);
        Bukkit.getPluginManager().registerEvents(new ServiceConnectListener(), this);
        getCommand("service").setExecutor(new ServiceCommand());
        PluginDriver.getInstance().register(new InformationCommand());
        PluginDriver.getInstance().register(new ShutdownCommand());
        new TimerBase().schedule(new TimerTask() {
            @Override
            public void run() {
                if (!NettyDriver.getInstance().nettyClient.getChannel().isActive()){
                    System.exit(0);
                }
            }
        }, 10, 10, TimeUtil.SECONDS);
    }
}
