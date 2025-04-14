package eu.schwicloud.manager.networking.node;

import eu.schwicloud.Driver;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.manager.CloudManager;
import eu.schwicloud.manager.cloudservices.entry.TaskedEntry;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.in.node.PacketInNodeActionSuccess;
import eu.schwicloud.terminal.enums.Type;
import eu.schwicloud.webserver.dummys.liveservice.LiveServices;
import io.netty.channel.Channel;

public class HandlePacketInNodeActionSuccess implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInNodeActionSuccess){
            if (((PacketInNodeActionSuccess) packet).isLaunched()){

                TaskedEntry entry = CloudManager.serviceDriver.getService(((PacketInNodeActionSuccess) packet).getService()).getEntry();
                entry.setUsedPort(((PacketInNodeActionSuccess) packet).getPort());
                LiveServices liveServices = (LiveServices) new ConfigDriver().convert(CloudManager.restDriver.get("/services/" + entry.getServiceName().replace(CloudManager.config.getSplitter(), "~")), LiveServices.class);
                liveServices.setPort(((PacketInNodeActionSuccess) packet).getPort());
                Driver.getInstance().getWebServer().updateRoute("/services/" + entry.getServiceName().replace(CloudManager.config.getSplitter(), "~"), new ConfigDriver().convert(liveServices));

                Driver.getInstance().getTerminalDriver().log(Type.INFO, Driver.getInstance().getLanguageDriver().getLang().getMessage("network-node-start")
                        .replace("%service%", ((PacketInNodeActionSuccess) packet).getService()).replace("%node%", ((PacketInNodeActionSuccess) packet).getNode())
                        .replace("%port%", "" +entry.getUsedPort()));
                CloudManager.serviceDriver.getService(((PacketInNodeActionSuccess) packet).getService()).getEntry().setUsedPort(((PacketInNodeActionSuccess) packet).getPort());
            }else {
                CloudManager.serviceDriver.unregister(((PacketInNodeActionSuccess) packet).getService());
            }
        }
    }
}
