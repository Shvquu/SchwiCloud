/*
 * this class is by RauchigesEtwas
 */

/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.bukkit.command.impli;

import com.velocitypowered.api.proxy.Player;
import eu.schwicloud.CloudAPI;
import eu.schwicloud.api.PluginCommand;
import eu.schwicloud.api.PluginCommandInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

@PluginCommandInfo(command = "shutdown", description = "/service shutdown")
public class ShutdownCommand extends PluginCommand {
    @Override
    public void performCommand(PluginCommand command, ProxiedPlayer proxiedPlayer, Player veloPlayer, org.bukkit.entity.Player bukkitPlayer, String[] args) {
        CloudAPI.getInstance().getServicePool().getService(CloudAPI.getInstance().getCurrentService().getService()).shutdown();
    }

    @Override
    public List<String> tabComplete(String[] args) {
        return new ArrayList<>();
    }
}
