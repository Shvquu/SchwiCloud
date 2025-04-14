/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.manager.networking.command;

import eu.schwicloud.Driver;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.manager.CloudManager;
import eu.schwicloud.networking.packet.NettyAdaptor;
import eu.schwicloud.networking.packet.Packet;
import eu.schwicloud.networking.packet.packets.in.service.command.PacketInCommandWhitelist;
import eu.schwicloud.webserver.dummys.WhiteList;
import io.netty.channel.Channel;

public class HandlePacketInCommandWhitelist implements NettyAdaptor {
    @Override
    public void handle(Channel channel, Packet packet) {
        if (packet instanceof PacketInCommandWhitelist){
            if (!CloudManager.config.getWhitelist().contains(((PacketInCommandWhitelist) packet).getName())){
                CloudManager.config.getWhitelist().add(((PacketInCommandWhitelist) packet).getName());
                new ConfigDriver("./service.json").save(CloudManager.config);
                WhiteList whitelistConfig = new WhiteList();
                whitelistConfig.setWhitelist(CloudManager.config.getWhitelist());
                Driver.getInstance().getWebServer().updateRoute("/default/whitelist", new ConfigDriver().convert(whitelistConfig));
            }   else {
                CloudManager.config.getWhitelist().remove(((PacketInCommandWhitelist) packet).getName());
                new ConfigDriver("./service.json").save(CloudManager.config);
                WhiteList whitelistConfig = new WhiteList();
                whitelistConfig.setWhitelist(CloudManager.config.getWhitelist());
                Driver.getInstance().getWebServer().updateRoute("/default/whitelist", new ConfigDriver().convert(whitelistConfig));
            }
        }
    }

}
