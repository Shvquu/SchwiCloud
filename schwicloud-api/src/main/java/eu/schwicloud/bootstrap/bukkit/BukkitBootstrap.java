package eu.schwicloud.bootstrap.bukkit;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.configuration.dummys.serviceconfig.LiveService;
import eu.schwicloud.networking.NettyDriver;
import eu.schwicloud.networking.packet.packets.in.service.PacketInServiceDisconnect;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitBootstrap extends JavaPlugin {

    @Override
    public void onLoad() {
        new CloudAPI(false);

    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("cloudservice-shutdown"));
        LiveService service = (LiveService) new ConfigDriver("./CLOUDSERVICE.json").read(LiveService.class);
        NettyDriver.getInstance().nettyClient.sendPacketSynchronized(new PacketInServiceDisconnect(service.getService()));
    }
}
