/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.manager;

import eu.schwicloud.Driver;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.configuration.dummys.authenticator.AuthenticatorKey;
import eu.schwicloud.configuration.dummys.managerconfig.ManagerConfig;
import eu.schwicloud.configuration.dummys.message.Messages;
import eu.schwicloud.manager.cloudservices.CloudServiceDriver;
import eu.schwicloud.manager.cloudservices.entry.TaskedService;
import eu.schwicloud.manager.cloudservices.queue.QueueDriver;
import eu.schwicloud.manager.commands.*;
import eu.schwicloud.manager.networking.command.HandlePacketInCommandMaintenance;
import eu.schwicloud.manager.networking.command.HandlePacketInCommandMaxPlayers;
import eu.schwicloud.manager.networking.command.HandlePacketInCommandMinCount;
import eu.schwicloud.manager.networking.command.HandlePacketInCommandWhitelist;
import eu.schwicloud.manager.networking.node.HandlePacketInAuthNode;
import eu.schwicloud.manager.networking.node.HandlePacketInNodeActionSuccess;
import eu.schwicloud.manager.networking.node.HandlePacketInSendConsole;
import eu.schwicloud.manager.networking.node.HandlePacketInShutdownNode;
import eu.schwicloud.manager.networking.service.*;
import eu.schwicloud.manager.networking.service.playerbased.HandlePacketInPlayerConnect;
import eu.schwicloud.manager.networking.service.playerbased.HandlePacketInPlayerDisconnect;
import eu.schwicloud.manager.networking.service.playerbased.HandlePacketInPlayerSwitchService;
import eu.schwicloud.networking.NettyDriver;
import eu.schwicloud.networking.packet.packets.in.node.PacketInAuthNode;
import eu.schwicloud.networking.packet.packets.in.node.PacketInNodeActionSuccess;
import eu.schwicloud.networking.packet.packets.in.node.PacketInSendConsole;
import eu.schwicloud.networking.packet.packets.in.node.PacketInShutdownNode;
import eu.schwicloud.networking.packet.packets.in.service.PacketInServiceConnect;
import eu.schwicloud.networking.packet.packets.in.service.PacketInServiceDisconnect;
import eu.schwicloud.networking.packet.packets.in.service.cloudapi.*;
import eu.schwicloud.networking.packet.packets.in.service.command.PacketInCommandMaintenance;
import eu.schwicloud.networking.packet.packets.in.service.command.PacketInCommandMaxPlayers;
import eu.schwicloud.networking.packet.packets.in.service.command.PacketInCommandMinCount;
import eu.schwicloud.networking.packet.packets.in.service.command.PacketInCommandWhitelist;
import eu.schwicloud.networking.packet.packets.in.service.playerbased.PacketInPlayerConnect;
import eu.schwicloud.networking.packet.packets.in.service.playerbased.PacketInPlayerDisconnect;
import eu.schwicloud.networking.packet.packets.in.service.playerbased.PacketInPlayerSwitchService;
import eu.schwicloud.networking.packet.packets.in.service.playerbased.apibased.*;
import eu.schwicloud.networking.packet.packets.out.node.*;
import eu.schwicloud.networking.server.NettyServer;
import eu.schwicloud.storage.IRunAble;
import eu.schwicloud.terminal.animation.AnimationDriver;
import eu.schwicloud.terminal.enums.Type;
import eu.schwicloud.webserver.RestDriver;
import eu.schwicloud.webserver.dummys.Addresses;
import eu.schwicloud.webserver.dummys.GroupList;
import eu.schwicloud.webserver.dummys.PlayerGeneral;
import eu.schwicloud.webserver.dummys.WhiteList;
import eu.schwicloud.webserver.dummys.liveservice.LiveServiceList;
import eu.schwicloud.webserver.entry.RouteEntry;

import java.io.File;
import java.util.*;

public class CloudManager implements IRunAble {

    public static CloudServiceDriver serviceDriver;
    public static QueueDriver queueDriver;
    public static RestDriver restDriver;
    public static ManagerConfig config;
    private static Timer base;
    public static boolean shutdown;

    public void initRestService(){
        Driver.getInstance().getTerminalDriver().log(Type.INFO, Driver.getInstance().getLanguageDriver().getLang().getMessage("web-server-prepared"));
        Driver.getInstance().runWebServer();
        Driver.getInstance().getTerminalDriver().log(Type.INFO, Driver.getInstance().getLanguageDriver().getLang().getMessage("web-server-connected").replace("%port%", "" + config.getRestApiCommunication()));
    }

    public static void shutdownHook(){
        shutdown = true;
        NettyDriver.getInstance().nettyServer.sendToAllSynchronized(new PacketOutShutdownNode());
        Driver.getInstance().getModuleDriver().unload();
        Driver.getInstance().getMessageStorage().eventDriver.clearListeners();
        NettyDriver.getInstance().nettyServer.close();
        serviceDriver.getServicesFromNode("InternalNode").forEach(TaskedService::handelQuit);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Driver.getInstance().getWebServer().close();

        Driver.getInstance().getTerminalDriver().log(Type.INFO, Driver.getInstance().getLanguageDriver().getLang().getMessage("cloud-shutting-down"));
        System.exit(0);
    }

    @Override
    public void run() {
        Driver.getInstance().getTerminalDriver().log(Type.INFO, Driver.getInstance().getLanguageDriver().getLang().getMessage("manager-try-start"));

        System.setProperty("io.netty.noPreferDirect", "true");
        System.setProperty("client.encoding.override", "UTF-8");
        System.setProperty("io.netty.maxDirectMemory", "0");
        System.setProperty("io.netty.leakDetectionLevel", "DISABLED");
        System.setProperty("io.netty.recycler.maxCapacity", "0");
        System.setProperty("io.netty.recycler.maxCapacity.default", "0");

        config = (ManagerConfig) new ConfigDriver("./service.json").read(ManagerConfig.class);
        if (!new File("./connection.key").exists()){
            AuthenticatorKey key = new AuthenticatorKey();
            String  k = UUID.randomUUID() + UUID.randomUUID().toString()+ UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID();
            key.setKey(Driver.getInstance().getMessageStorage().utf8ToUBase64(k));
            new ConfigDriver("./connection.key").save(key);
        }
        Driver.getInstance().getMessageStorage().canUseMemory = config.getCanUsedMemory() -250;
        initNetty(config);
        restDriver = new RestDriver(config.getManagerAddress(), config.getRestApiCommunication());
        initRestService();

        if (!new File("./local/messages.json").exists()){
            HashMap<String, String> messages = new HashMap<>();
            messages.put("prefix", "§8▷ §bMetaCloud §8▌ §7");
            messages.put("successfullyConnected", "%PREFIX%Successfully connected to §a%service_name%");
            messages.put("serviceIsFull", "%PREFIX%§cThe service is unfortunately full");
            messages.put("alreadyOnFallback", "%PREFIX%§cYou are already on a Fallback");
            messages.put("connectingGroupMaintenance", "%PREFIX%§cThe group is in maintenance");
            messages.put("noFallbackServer", "%PREFIX%§cCould not find a suitable fallback to connect you to!");
            messages.put("kickNetworkIsFull", "§8▷ §cThe network is full buy the premium to be able to despite that on it");
            messages.put("kickNetworkIsMaintenance", "§8▷ §cthe network is currently undergoing maintenance");
            messages.put("kickNoFallback", "§8▷ §cThe server you were on went down, but no fallback server was found!");
            messages.put("kickOnlyProxyJoin", "§8▷ §cpleas connect over the main proxy");
            messages.put("kickAlreadyOnNetwork", "§8▷ §cYou are already on the Network");
            messages.put("noPermsToJoinTheService", "§8▷ §cno perms to join the service");
            new ConfigDriver("./local/messages.json").save(new Messages(messages));
        }

        if (!new File("./local/GLOBAL/EVERY/plugins/metacloud-api.jar").exists()){
            Driver.getInstance().getTerminalDriver().log(Type.INFO,Driver.getInstance().getLanguageDriver().getLang().getMessage("try-to-download-cloudapi"));
            Driver.getInstance().getMessageStorage().packetLoader.loadAPI();
            new AnimationDriver().play();
        }
        if (!new File("./local/GLOBAL/EVERY/plugins/metacloud-plugin.jar").exists()){
            Driver.getInstance().getTerminalDriver().log(Type.INFO,Driver.getInstance().getLanguageDriver().getLang().getMessage("try-to-download-plugin"));
            Driver.getInstance().getMessageStorage().packetLoader.loadPlugin();
        }
        Driver.getInstance().getModuleDriver().load();

        Driver.getInstance().getTerminalDriver().log(Type.INFO, Driver.getInstance().getLanguageDriver().getLang().getMessage("try-to-load-commands"));
        Driver.getInstance().getTerminalDriver().getCommandDriver().registerCommand(new HelpCommand());
        Driver.getInstance().getTerminalDriver().getCommandDriver().registerCommand(new GroupCommand());
        Driver.getInstance().getTerminalDriver().getCommandDriver().registerCommand(new ClearCommand());
        Driver.getInstance().getTerminalDriver().getCommandDriver().registerCommand(new StopCommand());
        Driver.getInstance().getTerminalDriver().getCommandDriver().registerCommand(new ServiceCommand());
        Driver.getInstance().getTerminalDriver().getCommandDriver().registerCommand(new NodeCommand());
        Driver.getInstance().getTerminalDriver().getCommandDriver().registerCommand(new ReloadCommand());
        Driver.getInstance().getTerminalDriver().getCommandDriver().registerCommand(new MetaCloudCommand());
        Driver.getInstance().getTerminalDriver().getCommandDriver().registerCommand(new QueueCommand());
        Driver.getInstance().getTerminalDriver().getCommandDriver().registerCommand(new PlayersCommand());
        Driver.getInstance().getTerminalDriver().getCommandDriver().registerCommand(new ScreenCommand());
        Driver.getInstance().getTerminalDriver().getCommandDriver().registerCommand(new UpdateCommand());
        Driver.getInstance().getTerminalDriver().log(Type.INFO, Driver.getInstance().getLanguageDriver().getLang().getMessage("commands-was-loaded")
                .replace("%commands%", "" + Driver.getInstance().getTerminalDriver().getCommandDriver().getCommands().size()));
        Driver.getInstance().getTerminalDriver().log(Type.INFO, Driver.getInstance().getLanguageDriver().getLang().getMessage("cloud-start-successful"));
        queueDriver= new QueueDriver();
        Messages msg = (Messages) new ConfigDriver("./local/messages.json").read(Messages.class);
        Driver.getInstance().getWebServer().addRoute(new RouteEntry("/message/default", new ConfigDriver().convert(msg)));
        GroupList groupList = new GroupList();
        groupList.setGroups(Driver.getInstance().getGroupDriver().getAllStrings());


        Driver.getInstance().getWebServer().addRoute(new RouteEntry("/cloudgroup/general", new ConfigDriver().convert(groupList)));
        Driver.getInstance().getGroupDriver().getAll().forEach(group -> {
            if (Driver.getInstance().getWebServer().getRoute("/cloudgroup/" +group.getGroup()) == null){
                Driver.getInstance().getWebServer().addRoute(new RouteEntry("/cloudgroup/" + group.getGroup(), new ConfigDriver().convert(group)));

            }else {
                Driver.getInstance().getWebServer().updateRoute("/cloudgroup/" + group.getGroup(), new ConfigDriver().convert(group));
            }
        });

        Driver.getInstance().handleOfflinePlayerCacheDriver();
        LiveServiceList liveGroup = new LiveServiceList();
        liveGroup.setCloudServiceSplitter(config.getSplitter());
        liveGroup.setCloudServices(new ArrayDeque<>());
        Driver.getInstance().getWebServer().addRoute(new RouteEntry("/cloudservice/general", new ConfigDriver().convert(liveGroup)));
        WhiteList whitelistConfig = new WhiteList();
        whitelistConfig.setWhitelist(config.getWhitelist());
        Driver.getInstance().getWebServer().addRoute(new RouteEntry("/default/whitelist", new ConfigDriver().convert(whitelistConfig)));
        PlayerGeneral general = new PlayerGeneral();
        general.setCloudplayers(new ArrayList<>());
        Driver.getInstance().getWebServer().addRoute(new RouteEntry("/cloudplayer/genernal", new ConfigDriver().convert(general)));

        Addresses AddressesConfig = new Addresses();
        ArrayList<String> addresses = new ArrayList<>();
        config.getNodes().forEach(managerConfigNodes -> {
            addresses.add(managerConfigNodes.getAddress());
        });
        AddressesConfig.setWhitelist(addresses);
        Driver.getInstance().getWebServer().addRoute(new RouteEntry("/default/addresses", new ConfigDriver().convert(AddressesConfig)));
        serviceDriver = new CloudServiceDriver();
    }

    public void initNetty(ManagerConfig config){
        new NettyDriver();
        Driver.getInstance().getTerminalDriver().log(Type.NETWORK, Driver.getInstance().getLanguageDriver().getLang().getMessage("netty-server-prepared"));

        NettyDriver.getInstance().getWhitelist().add("127.0.0.1");
        config.getNodes().forEach(managerConfigNodes -> {
            if (!NettyDriver.getInstance().getWhitelist().contains(managerConfigNodes.getAddress())){
                NettyDriver.getInstance().getWhitelist().add(managerConfigNodes.getAddress());
            }
        });
        /*
         * this starts a new NettyServer with Epoll on EpollEventLoopGroup or NioEventLoopGroup basis.
         * */

        NettyDriver.getInstance().nettyServer = new NettyServer();
        NettyDriver.getInstance().nettyServer.bind( config.getNetworkingCommunication()).start();
        Driver.getInstance().getTerminalDriver().log(Type.NETWORK, Driver.getInstance().getLanguageDriver().getLang().getMessage("netty-server-connected")
                .replace("%address%", config.getManagerAddress()).replace("%port%", "" + config.getNetworkingCommunication()));

        //PACKETS
        NettyDriver.getInstance().getPacketDriver()
                /*
                 * in this part all packages and traders sent to the server are registered
                 * {@link NettyAdaptor} handles the packet and looks where it belongs
                 * {@link Packet} handles the packets are written and read via a ByteBuf
                 * */
                //NODE
                .registerHandler(new PacketInAuthNode().getPacketUUID(), new HandlePacketInAuthNode(), PacketInAuthNode.class)
                .registerHandler(new PacketInNodeActionSuccess().getPacketUUID(), new HandlePacketInNodeActionSuccess(), PacketInNodeActionSuccess.class)
                .registerHandler(new PacketInShutdownNode().getPacketUUID(), new HandlePacketInShutdownNode(), PacketInShutdownNode.class)
                .registerHandler(new PacketInSendConsole().getPacketUUID(), new HandlePacketInSendConsole(), PacketInSendConsole.class)
                //API
                .registerHandler(new PacketInServiceConnect().getPacketUUID(), new HandlePacketInServiceConnect(), PacketInServiceConnect.class)
                .registerHandler(new PacketInServiceDisconnect().getPacketUUID(), new HandlePacketInServiceDisconnect(), PacketInServiceDisconnect.class)
                .registerHandler(new PacketInChangeState().getPacketUUID(), new HandlePacketInChangeState(), PacketInChangeState.class)
                .registerHandler(new PacketInDispatchCommand().getPacketUUID(), new HandlePacketInDispatchCommand(), PacketInDispatchCommand.class)
                .registerHandler(new PacketInDispatchMainCommand().getPacketUUID(), new HandlePacketInDispatchMainCommand(), PacketInDispatchMainCommand.class)
                .registerHandler(new PacketInLaunchService().getPacketUUID(), new HandlePacketInLaunchService(), PacketInLaunchService.class)
                .registerHandler(new PacketInStopGroup().getPacketUUID(), new HandlePacketInStopGroup(), PacketInStopGroup.class)
                .registerHandler(new PacketInStopService().getPacketUUID(), new HandlePacketInStopService(), PacketInStopService.class)
                .registerHandler(new PacketInAPIPlayerMessage().getPacketUUID(), new HandlePacketInAPIPlayerMessage(), PacketInAPIPlayerMessage.class)
                .registerHandler(new PacketInAPIPlayerConnect().getPacketUUID(), new HandlePacketInAPIPlayerConnect(), PacketInAPIPlayerConnect.class)
                .registerHandler(new PacketInAPIPlayerKick().getPacketUUID(), new HandlePacketInAPIPlayerKick(), PacketInAPIPlayerKick.class)
                .registerHandler(new PacketInAPIPlayerTitle().getPacketUUID(), new HandlePacketInAPIPlayerTitle(), PacketInAPIPlayerTitle.class)
                .registerHandler(new PacketInAPIPlayerActionBar().getPacketUUID(), new HandlePacketInAPIPlayerActionBar(), PacketInAPIPlayerActionBar.class)
                .registerHandler(new PacketInAPIPlayerTab().getPacketUUID(), new HandlePacketInAPIPlayerTab(), PacketInAPIPlayerTab.class)
                .registerHandler(new PacketInCloudPlayerComponent().getPacketUUID(), new HandlePacketInCloudPlayerComponent(), PacketInCloudPlayerComponent.class)
                .registerHandler(new PacketOutAPIPlayerDispactchCommand().getPacketUUID(), new HandlePacketOutAPIPlayerDispatchCommand(), PacketOutAPIPlayerDispactchCommand.class)
                .registerHandler(new PacketInCreateGroup().getPacketUUID(), new HandlePacketInCreateGroup(), PacketInCreateGroup.class)
                .registerHandler(new PacketInDeleteGroup().getPacketUUID(), new HandlePacketInDeleteGroup(), PacketInDeleteGroup.class)
                .registerHandler(new PacketLaunchServiceWithCustomTemplate().getPacketUUID(), new HandlePacketInDeleteGroup(), PacketLaunchServiceWithCustomTemplate.class)


                //COMMAND
                .registerHandler(new PacketInCommandMaintenance().getPacketUUID(), new HandlePacketInCommandMaintenance(), PacketInCommandMaintenance.class)
                .registerHandler(new PacketInCommandMaxPlayers().getPacketUUID(),new HandlePacketInCommandMaxPlayers(), PacketInCommandMaxPlayers.class)
                .registerHandler(new PacketInCommandMinCount().getPacketUUID(),new HandlePacketInCommandMinCount(), PacketInCommandMinCount.class)
                .registerHandler(new PacketInCommandWhitelist().getPacketUUID(),new HandlePacketInCommandWhitelist(), PacketInCommandWhitelist.class)

                //PLAYER
                .registerHandler(new PacketInPlayerConnect().getPacketUUID(), new HandlePacketInPlayerConnect(), PacketInPlayerConnect.class)
                .registerHandler(new PacketInPlayerDisconnect().getPacketUUID(), new HandlePacketInPlayerDisconnect(), PacketInPlayerDisconnect.class)
                .registerHandler(new PacketInPlayerSwitchService().getPacketUUID(), new HandlePacketInPlayerSwitchService(), PacketInPlayerSwitchService.class);

    }

    public static void screenNode(String node){
        if (Driver.getInstance().getMessageStorage().openServiceScreen){
            base.cancel();
            NettyDriver.getInstance().nettyServer.sendPacketSynchronized(node, new PacketOutDisableNodeConsole());
            Driver.getInstance().getTerminalDriver().leaveSetup();
        }else {
            Driver.getInstance().getMessageStorage().openServiceScreen = true;
            Driver.getInstance().getMessageStorage().screenForm = node;
            NettyDriver.getInstance().nettyServer.sendPacketSynchronized(node, new PacketOutEnableNodeConsole());
            Driver.getInstance().getTerminalDriver().clearScreen();

            base = new Timer();
            base.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (! Driver.getInstance().getMessageStorage().consoleInput.isEmpty()){
                        String line =  Driver.getInstance().getMessageStorage().consoleInput.removeFirst();
                        if (line.equalsIgnoreCase("leave") ||line.equalsIgnoreCase("leave ")){
                            screenNode(Driver.getInstance().getMessageStorage().screenForm);
                        }else {
                            NettyDriver.getInstance().nettyServer.sendPacketSynchronized(node, new PacketOutSendCommandToNodeConsole(line));
                        }
                    }
                }
            }, 100, 100);
         }
    }
}
