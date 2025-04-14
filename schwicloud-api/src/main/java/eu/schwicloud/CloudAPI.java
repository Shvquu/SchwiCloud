package eu.schwicloud;

import eu.schwicloud.group.async.AsyncGroupPool;
import eu.schwicloud.networking.packet.packets.out.service.events.*;
import eu.schwicloud.offlineplayer.async.AsyncOfflinePlayerPool;
import eu.schwicloud.player.async.AsyncPlayerPool;
import eu.schwicloud.player.async.entrys.AsyncCloudPlayer;
import eu.schwicloud.service.async.AsyncServicePool;
import eu.schwicloud.bootstrap.bungee.listener.CloudEvents;
import eu.schwicloud.bootstrap.bungee.networking.*;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.configuration.dummys.message.Messages;
import eu.schwicloud.configuration.dummys.serviceconfig.LiveService;
import eu.schwicloud.events.EventDriver;
import eu.schwicloud.events.entrys.ICloudListener;
import eu.schwicloud.groups.dummy.Group;
import eu.schwicloud.networking.*;
import eu.schwicloud.networking.client.NettyClient;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.in.service.PacketInServiceConnect;
import eu.schwicloud.networking.packet.packets.in.service.cloudapi.PacketInChangeState;
import eu.schwicloud.networking.packet.packets.in.service.cloudapi.PacketInDispatchMainCommand;
import eu.schwicloud.networking.packet.packets.in.service.command.PacketInCommandWhitelist;
import eu.schwicloud.networking.packet.packets.out.service.*;
import eu.schwicloud.networking.packet.packets.out.service.group.PacketOutGroupCreate;
import eu.schwicloud.networking.packet.packets.out.service.group.PacketOutGroupDelete;
import eu.schwicloud.networking.packet.packets.out.service.group.PacketOutGroupEdit;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.PacketOutPlayerConnect;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.PacketOutPlayerDisconnect;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.PacketOutPlayerSwitchService;
import eu.schwicloud.networking.packet.packets.out.service.playerbased.apibased.*;
import eu.schwicloud.group.GroupPool;
import eu.schwicloud.offlineplayer.OfflinePlayerPool;
import eu.schwicloud.player.PlayerPool;
import eu.schwicloud.player.entrys.CloudPlayer;
import eu.schwicloud.service.ServicePool;
import eu.schwicloud.service.entrys.CloudService;
import eu.schwicloud.process.ServiceState;
import eu.schwicloud.storage.UUIDDriver;
import eu.schwicloud.timebaser.TimerBase;
import eu.schwicloud.timebaser.utils.TimeUtil;
import eu.schwicloud.webserver.RestDriver;
import eu.schwicloud.webserver.dummys.PlayerGeneral;
import eu.schwicloud.webserver.dummys.WhiteList;
import eu.schwicloud.webserver.dummys.liveservice.LiveServiceList;
import eu.schwicloud.webserver.dummys.liveservice.LiveServices;
import lombok.Getter;
import lombok.NonNull;

import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Objects;
import java.util.TimerTask;
@Getter
public class CloudAPI {

    @Getter
    private static CloudAPI instance;

    private final LiveService service;
    private final PlayerPool playerPool;
    private final OfflinePlayerPool offlinePlayerPool;
    private final AsyncOfflinePlayerPool asyncOfflinePlayerPool;
    private final GroupPool groupPool;
    private final ServicePool servicePool;
    private final RestDriver restDriver;
    private final EventDriver eventDriver;
    private final AsyncPlayerPool asyncPlayerPool;
    private final AsyncServicePool asyncServicePool;
    private final AsyncGroupPool asyncGroupPool;

    public CloudAPI(boolean isVelocity) {
        instance = this;
        new Driver();
        service = (LiveService) new ConfigDriver("./CLOUDSERVICE.json").read(LiveService.class);
        new NettyDriver();


        restDriver = new RestDriver(service.getManagerAddress(), service.getRestPort());
        NettyDriver.getInstance().nettyClient = new NettyClient();
        NettyDriver.getInstance().nettyClient.bind(service.getManagerAddress(), service.getNetworkPort()).connect();

        registerHandlers();
        this.offlinePlayerPool = new OfflinePlayerPool();
        this.asyncOfflinePlayerPool = new AsyncOfflinePlayerPool();
        this.playerPool = new PlayerPool();
        this.servicePool = new ServicePool();
        this.groupPool = new GroupPool();
        this.asyncGroupPool = new AsyncGroupPool();
        this.asyncServicePool = new AsyncServicePool();
        this.asyncPlayerPool = new AsyncPlayerPool();
        this.eventDriver = new EventDriver();

        Group group = (Group) new ConfigDriver().convert(CloudAPI.getInstance().getRestDriver().get("/cloudgroup/" + service.getGroup()), Group.class);
        PlayerGeneral players = (PlayerGeneral) new ConfigDriver().convert(CloudAPI.getInstance().getRestDriver().get("/cloudplayer/genernal"), PlayerGeneral.class);
        players.getCloudplayers().forEach(s -> {
            if (!CloudAPI.getInstance().getPlayerPool().playerIsNotNull(s)){
                getAsyncPlayerPool().registerPlayer(new AsyncCloudPlayer(s, Objects.requireNonNull(UUIDDriver.getUUID(s))));
                getPlayerPool().registerPlayer(new CloudPlayer(s, Objects.requireNonNull(UUIDDriver.getUUID(s))));
            }
        });
        if (group.getGroupType().equals("PROXY")) {
            if (isVelocity) {
                this.registerVelocityHandlers();
            } else {
                this.registerBungeeHandlers();
            }
        }

        NettyDriver.getInstance().nettyClient.sendPacketSynchronized(new PacketInServiceConnect(service.getService()));

        new TimerBase().schedule(new TimerTask() {
            @Override
            public void run() {
                CloudService service = CloudAPI.getInstance().getServicePool().getService(CloudAPI.getInstance().getCurrentService().getService());
                LiveServiceList list = (LiveServiceList) new ConfigDriver().convert(CloudAPI.getInstance().getRestDriver().get("/cloudservice/general"), LiveServiceList.class);
                PlayerGeneral general = (PlayerGeneral) new ConfigDriver().convert(CloudAPI.getInstance().getRestDriver().get("/cloudplayer/genernal"), PlayerGeneral.class);
                general.getCloudplayers().forEach(s -> {
                    if (!CloudAPI.getInstance().getPlayerPool().playerIsNotNull(s)){
                        getAsyncPlayerPool().registerPlayer(new AsyncCloudPlayer(s, Objects.requireNonNull(UUIDDriver.getUUID(s))));
                        getPlayerPool().registerPlayer(new CloudPlayer(s, Objects.requireNonNull(UUIDDriver.getUUID(s))));
                    }
                });
                getPlayerPool().getPlayers().stream().filter(cloudPlayer -> general.getCloudplayers().stream().noneMatch(s -> s.equalsIgnoreCase(cloudPlayer.getUniqueId().toString()))).toList().forEach(cloudPlayer -> {
                    getPlayerPool().unregisterPlayer(cloudPlayer.getUniqueId());
                    getAsyncPlayerPool().unregisterPlayer(cloudPlayer.getUniqueId());
                });
                getServicePool().getServices().stream().filter(cloudService -> list.getCloudServices().stream().noneMatch(s -> s.equalsIgnoreCase(cloudService.getName()))).toList().forEach(cloudService -> {
                    getServicePool().unregisterService(cloudService.getName());
                    getAsyncServicePool().unregisterService(cloudService.getName());
                });
                if (!NettyDriver.getInstance().nettyClient.getChannel().isOpen()){
                    System.exit(0);
                }
                LiveServices liveServices = (LiveServices) new ConfigDriver().convert(CloudAPI.getInstance().getRestDriver().get("/cloudservice/" + service.getName().replace(list.getCloudServiceSplitter(), "~")), LiveServices.class);
                liveServices.setLastReaction(System.currentTimeMillis());
                CloudAPI.getInstance().getRestDriver().update("/cloudservice/" + service.getName().replace(list.getCloudServiceSplitter(), "~"), new ConfigDriver().convert(liveServices));

            }
        }, 30, 30, TimeUtil.SECONDS);

    }



    public void dispatchCommand(String command){
        sendPacketSynchronized(new PacketInDispatchMainCommand(command));
    }

    public LiveService getCurrentService(){
        return service;
    }

    public List<String> getWhitelist(){
        WhiteList cech = (WhiteList) new ConfigDriver().convert(CloudAPI.getInstance().getRestDriver().get("/default/whitelist"), WhiteList.class);
        return cech.getWhitelist();
    }

    public Messages getMessages(){
        return (Messages) new ConfigDriver().convert(CloudAPI.getInstance().getRestDriver().get("/message/default"), Messages.class);
    }

    public boolean addWhiteList(String username){
        if (getWhitelist().stream().noneMatch(s -> s.equals(username))){
            CloudAPI.getInstance().sendPacketSynchronized(new PacketInCommandWhitelist(username));
            return true;
        }
        return false;
    }

    public boolean removeWhiteList(String username){
        if (getWhitelist().stream().anyMatch(s -> s.equals(username))){
            if (getPlayerPool().getPlayer(username) != null) getPlayerPool().getPlayer(username).disconnect(getMessages().getMessages().get("kickNetworkIsMaintenance"));
            CloudAPI.getInstance().sendPacketSynchronized(new PacketInCommandWhitelist(username));
            return true;
        }
        return false;
    }

    public void registerListener(ICloudListener eventListener){
        eventDriver.registerListener(eventListener);
    }

    public void setState(@NonNull ServiceState state, String name){
        CloudAPI.getInstance().sendPacketSynchronized(new PacketInChangeState(name, state.toString()));
    }

    public void setState(ServiceState state){
        setState(state, getCurrentService().getService());
    }
    
    public double getUsedMemory(){
        return  (double) ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() / 1048576;
    }

    public double getMaxMemory(){
        return  (double) ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax() / 1048576;
    }

    public void sendPacketSynchronized(Packet packet){
        NettyDriver.getInstance().nettyClient.sendPacketSynchronized(packet);
    }

    public void sendPacketAsynchronous(Packet packet){
        NettyDriver.getInstance().nettyClient.sendPacketsAsynchronous(packet);
    }


    private void  registerHandlers(){
        NettyDriver.getInstance().getPacketDriver()
                .registerHandler(new PacketOutCloudServiceCouldNotStartEvent().getPacketUUID(), new HandlePacketOutCloudServiceCouldNotStartEvent(), PacketOutCloudServiceCouldNotStartEvent.class)
                .registerHandler(new PacketOutCloudProxyCouldNotStartEvent().getPacketUUID(), new HandlePacketOutCloudProxyCouldNotStartEvent(), PacketOutCloudProxyCouldNotStartEvent.class)
                .registerHandler(new PacketOutServicePrepared().getPacketUUID(), new HandlePacketOutServicePrepared(), PacketOutServicePrepared.class)
                .registerHandler(new PacketOutServiceConnected().getPacketUUID(), new HandlePacketOutServiceConnected(), PacketOutServiceConnected.class)
                .registerHandler(new PacketOutServiceDisconnected().getPacketUUID(), new HandlePacketOutServiceDisconnected(), PacketOutServiceDisconnected.class)
                .registerHandler(new PacketOutPlayerConnect().getPacketUUID(), new HandlePacketOutPlayerConnect(), PacketOutPlayerConnect.class)
                .registerHandler(new PacketOutPlayerDisconnect().getPacketUUID(), new HandlePacketOutPlayerDisconnect(), PacketOutPlayerDisconnect.class)
                .registerHandler(new PacketOutPlayerSwitchService().getPacketUUID(), new HandlePacketOutPlayerSwitchService(), PacketOutPlayerSwitchService.class)
                .registerHandler(new PacketOutServiceLaunch().getPacketUUID(), new HandlePacketOutServiceLaunch(), PacketOutServiceLaunch.class)
                .registerHandler(new PacketOutGroupCreate().getPacketUUID(), new HandlePacketOutGroupCreate(), PacketOutGroupCreate.class)
                .registerHandler(new PacketOutGroupDelete().getPacketUUID(), new HandlePacketOutGroupDelete(), PacketOutGroupDelete.class)
                .registerHandler(new PacketOutGroupEdit().getPacketUUID(), new HandlePacketOutGroupEdit(), PacketOutGroupEdit.class)

                .registerHandler(new PacketOutCloudRestAPIReloadEvent().getPacketUUID(), new HandlePacketOutCloudRestAPIReloadEvent(), PacketOutCloudRestAPIReloadEvent.class)
                .registerHandler(new PacketOutCloudRestAPICreateEvent().getPacketUUID(), new HandlePacketOutCloudRestAPICreateEvent(), PacketOutCloudRestAPICreateEvent.class)
                .registerHandler(new PacketOutCloudRestAPIUpdateEvent().getPacketUUID(), new HandlePacketOutCloudRestAPIUpdateEvent(), PacketOutCloudRestAPIUpdateEvent.class)
                .registerHandler(new PacketOutCloudRestAPIDeleteEvent().getPacketUUID(), new HandlePacketOutCloudRestAPIDeleteEvent(), PacketOutCloudRestAPIDeleteEvent.class)


                .registerHandler(new PacketOutCloudServiceChangeState().getPacketUUID(), new HandlePacketOutCloudServiceChangeState(), PacketOutCloudServiceChangeState.class)
                .registerHandler(new PacketOutCloudProxyChangeState().getPacketUUID(), new HandlePacketOutCloudProxyChangeState(), PacketOutCloudProxyChangeState.class);
    }

    private void registerVelocityHandlers() {
        eventDriver.registerListener(new eu.schwicloud.bootstrap.velocity.listener.CloudEvents());
        NettyDriver.getInstance().getPacketDriver()
                .registerHandler(new PacketOutAPIPlayerConnect().getPacketUUID(), new eu.schwicloud.bootstrap.velocity.networking.HandlePacketOutAPIPlayerConnect(), PacketOutAPIPlayerConnect.class)
                .registerHandler(new PacketOutAPIPlayerMessage().getPacketUUID(), new eu.schwicloud.bootstrap.velocity.networking.HandlePacketOutAPIPlayerMessage(), PacketOutAPIPlayerMessage.class)
                .registerHandler(new PacketOutAPIPlayerTitle().getPacketUUID(), new eu.schwicloud.bootstrap.velocity.networking.HandlePacketOutAPIPlayerTitle(), PacketOutAPIPlayerTitle.class)
                .registerHandler(new PacketOutAPIPlayerActionBar().getPacketUUID(), new eu.schwicloud.bootstrap.velocity.networking.HandlePacketOutAPIPlayerActionBar(), PacketOutAPIPlayerActionBar.class)
                .registerHandler(new PacketOutAPIPlayerDispactchCommand().getPacketUUID(), new eu.schwicloud.bootstrap.velocity.networking.HandlePacketOutAPIPlayerDispactchCommand(), PacketOutAPIPlayerDispactchCommand.class)
                .registerHandler(new PacketOutAPIPlayerKick().getPacketUUID(), new eu.schwicloud.bootstrap.velocity.networking.HandlePacketOutAPIPlayerKick(), PacketOutAPIPlayerKick.class)
                .registerHandler(new PacketOutCloudPlayerComponent().getPacketUUID(), new eu.schwicloud.bootstrap.velocity.networking.HandlePacketOutCloudPlayerComponent(), PacketOutCloudPlayerComponent.class)
                .registerHandler(new PacketOutAPIPlayerTab().getPacketUUID(), new eu.schwicloud.bootstrap.velocity.networking.HandlePacketOutAPIPlayerTab(), PacketOutAPIPlayerTab.class);

    }

    private void registerBungeeHandlers() {
        eventDriver.registerListener(new CloudEvents());
        NettyDriver.getInstance().getPacketDriver()
                .registerHandler(new PacketOutAPIPlayerConnect().getPacketUUID(), new HandlePacketOutAPIPlayerConnect(), PacketOutAPIPlayerConnect.class)
                .registerHandler(new PacketOutAPIPlayerMessage().getPacketUUID(), new HandlePacketOutAPIPlayerMessage(), PacketOutAPIPlayerMessage.class)
                .registerHandler(new PacketOutAPIPlayerTitle().getPacketUUID(), new HandlePacketOutAPIPlayerTitle(), PacketOutAPIPlayerTitle.class)
                .registerHandler(new PacketOutAPIPlayerActionBar().getPacketUUID(), new HandlePacketOutAPIPlayerActionBar(), PacketOutAPIPlayerActionBar.class)
                .registerHandler(new PacketOutAPIPlayerKick().getPacketUUID(), new HandlePacketOutAPIPlayerKick(), PacketOutAPIPlayerKick.class)
                .registerHandler(new PacketOutCloudPlayerComponent().getPacketUUID(), new HandlePacketOutCloudPlayerComponent(), PacketOutCloudPlayerComponent.class)
                .registerHandler(new PacketOutAPIPlayerDispactchCommand().getPacketUUID(), new HandlePacketOutAPIPlayerDispactchCommand(), PacketOutAPIPlayerDispactchCommand.class)
                .registerHandler(new PacketOutAPIPlayerTab().getPacketUUID(), new HandlePacketOutAPIPlayerTab(), PacketOutAPIPlayerTab.class);
    }


}
