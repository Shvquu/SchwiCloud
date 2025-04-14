/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.subcommands;

import com.velocitypowered.api.proxy.Player;
import eu.schwicloud.CloudAPI;
import eu.schwicloud.Driver;
import eu.schwicloud.api.PluginCommand;
import eu.schwicloud.api.PluginCommandInfo;
import eu.schwicloud.configuration.dummys.message.Messages;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

@PluginCommandInfo(command = "version", description = "/cloud version")
public class VersionCommand extends PluginCommand {
    @Override
    public void performCommand(PluginCommand command, ProxiedPlayer proxiedPlayer, Player veloPlayer, org.bukkit.entity.Player bukkitPlayer, String[] args) {
        Messages messages = CloudAPI.getInstance().getMessages();
        String PREFIX = messages.getMessages().get("prefix").replace("&", "§");
        if (proxiedPlayer != null) {
            proxiedPlayer.sendMessage(PREFIX + "The cloud is currently running on version §8⯮ §f" + Driver.getInstance().getMessageStorage().version);
        } else {
            veloPlayer.sendMessage(Component.text(PREFIX + "The cloud is currently running on version §8⯮ §f" + Driver.getInstance().getMessageStorage().version));
        }
    }

    @Override
    public List<String> tabComplete(String[] args) {
        return new ArrayList<>();
    }
}
