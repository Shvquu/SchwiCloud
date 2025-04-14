package eu.schwicloud.bungee;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.Driver;
import eu.schwicloud.api.PluginDriver;
import eu.schwicloud.bungee.command.CloudCommand;
import eu.schwicloud.bungee.listener.CloudConnectListener;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.configuration.dummys.message.Messages;
import eu.schwicloud.configuration.dummys.serviceconfig.LiveService;
import eu.schwicloud.networking.NettyDriver;
import eu.schwicloud.service.entrys.CloudService;
import eu.schwicloud.process.ServiceState;
import eu.schwicloud.subcommands.*;
import eu.schwicloud.timebaser.TimerBase;
import eu.schwicloud.timebaser.utils.TimeUtil;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.*;

public class BungeeBootstrap extends Plugin {


    private static BungeeBootstrap instance;
    public BungeeAudiences audiences;
    @Override
    public void onEnable() {
        instance = this;
        new Driver();
        audiences = BungeeAudiences.builder(instance).build();
        new PluginDriver();
                LiveService service = (LiveService) new ConfigDriver("./CLOUDSERVICE.json").read(LiveService.class);
        CloudAPI.getInstance().setState(ServiceState.LOBBY, service.getService());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new CloudConnectListener());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CloudCommand("cloud"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CloudCommand("metacloud"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CloudCommand("mc"));
        PluginDriver.getInstance().register(new ExitCommand());
        PluginDriver.getInstance().register(new VersionCommand());
        PluginDriver.getInstance().register(new ReloadCommand());
        PluginDriver.getInstance().register(new ServiceCommand());
        PluginDriver.getInstance().register(new GroupCommand());
        PluginDriver.getInstance().register(new PlayerCommand());
        new TimerBase().schedule(new TimerTask() {
            @Override
            public void run() {
                if (CloudAPI.getInstance().getGroupPool().getGroup(service.getGroup()).isMaintenance()){
                    ProxyServer.getInstance().getPlayers().forEach(player -> {
                       if ( !player.hasPermission("metacloud.connection.maintenance") && !CloudAPI.getInstance().getWhitelist().contains(player.getName())){
                           Messages messages = CloudAPI.getInstance().getMessages();
                           player.disconnect(Driver.getInstance().getMessageStorage().base64ToUTF8(messages.getMessages().get("kickNetworkIsMaintenance")).replace("&", "§"));
                       }
                    });
                }
                    if (!NettyDriver.getInstance().nettyClient.getChannel().isActive()){
                        System.exit(0);
                    }
            }
        }, 2, 2, TimeUtil.SECONDS);
    }

    public static BungeeBootstrap getInstance() {
        return instance;
    }

    public CloudService getLobby(ProxiedPlayer player){
        if (CloudAPI.getInstance().getServicePool().getServices().isEmpty()){
            return null;
        }else if (CloudAPI.getInstance().getServicePool().getServices().stream().noneMatch(service -> service.getGroup().getGroupType().equalsIgnoreCase("LOBBY")  && service.getState() == ServiceState.LOBBY)){
            return null;
        }else {
            List<CloudService> cloudServices = CloudAPI.getInstance().getServicePool().getServices().stream()
                    .filter(service -> service.getGroup().getGroupType().equalsIgnoreCase("LOBBY"))
                    .filter(service -> !service.getGroup().isMaintenance())
                    .filter(service -> service.getState() == ServiceState.LOBBY).toList()
                    .stream().filter(service -> {
                        if (service.getGroup().getPermission().equalsIgnoreCase("")) {
                            return true;
                        } else return player.hasPermission(service.getGroup().getPermission());
                    }).toList();
            if (cloudServices.isEmpty()){
                return null;
            }
            List<Integer> priority = new ArrayList<>();
            cloudServices.forEach( service -> priority.add(service.getGroup().getPriority()));
            priority.sort(Collections.reverseOrder());
            int priorty = priority.get(0);
            List<CloudService> lobbys = cloudServices.stream().filter(service -> service.getGroup().getPriority() == priorty).toList();
            return  lobbys.get(new Random().nextInt(lobbys.size()));
        }
    }

    public CloudService getLobby(ProxiedPlayer player, String kicked){
        if (CloudAPI.getInstance().getServicePool().getServices().isEmpty()){
            return null;
        }else if (CloudAPI.getInstance().getServicePool().getServices().stream().noneMatch(service -> service.getGroup().getGroupType().equals("LOBBY") && service.getState() == ServiceState.LOBBY)){
            return null;
        }
        List<CloudService> services = CloudAPI.getInstance().getServicePool().getServices().stream()
                .filter(service -> service.getGroup().getGroupType().equals("LOBBY"))
                .filter(service -> !service.getGroup().isMaintenance())
                .filter(service -> !service.getName().equals(kicked))
                .filter(service -> service.getState() == ServiceState.LOBBY)
                .filter(service -> service.getGroup().getPermission().equals("") || player.hasPermission(service.getGroup().getPermission())).toList();

        if (services.isEmpty()){
            return null;
        }
        List<Integer> priority = new ArrayList<>();
        services.forEach( service -> priority.add(service.getGroup().getPriority()));
        priority.sort(Collections.reverseOrder());
        int priorty = priority.get(0);
        List<CloudService> lobbys = services.stream().filter(service -> service.getGroup().getPriority() == priorty).toList();
        if (lobbys.size() == 0){
            return null;
        }
        return  lobbys.get(new Random().nextInt(lobbys.size()));
    }
}
