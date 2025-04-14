/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.subcommands;

import com.velocitypowered.api.proxy.Player;
import eu.schwicloud.CloudAPI;
import eu.schwicloud.api.PluginCommand;
import eu.schwicloud.api.PluginCommandInfo;
import eu.schwicloud.configuration.dummys.message.Messages;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

@PluginCommandInfo(command = "reload", description = "/cloud reload")
public class ReloadCommand extends PluginCommand {

    @Override
    public void performCommand(PluginCommand command, ProxiedPlayer proxiedPlayer, Player veloPlayer, org.bukkit.entity.Player bukkitPlayer, String[] args) {
        if (args.length == 0){
            if (proxiedPlayer == null){
                veloPlayer.sendMessage(Component.text(getHelp()));
            }else {
                proxiedPlayer.sendMessage(getHelp());
            }
        }else {
            Messages messages = CloudAPI.getInstance().getMessages();
            String PREFIX = messages.getMessages().get("prefix").replace("&", "§");
            if (args[0].equalsIgnoreCase("all")){
                if (proxiedPlayer == null){
                    veloPlayer.sendMessage(Component.text(PREFIX + "the whole cloud was reloaded"));
                }else {
                    proxiedPlayer.sendMessage(PREFIX + "the whole cloud was reloaded");
                }
                CloudAPI.getInstance().dispatchCommand("reload all");
            }else if (args[0].equalsIgnoreCase("modules")){
                if (proxiedPlayer == null){
                    veloPlayer.sendMessage(Component.text(PREFIX + "the modules was reloaded"));
                }else {
                    proxiedPlayer.sendMessage(PREFIX + "the modules was reloaded");
                }
                CloudAPI.getInstance().dispatchCommand("reload modules");
            }else if (args[0].equalsIgnoreCase("config")){
                if (proxiedPlayer == null){
                    veloPlayer.sendMessage(Component.text(PREFIX + "the config was reloaded"));
                }else {
                    proxiedPlayer.sendMessage(PREFIX + "the config was reloaded");
                }
                CloudAPI.getInstance().dispatchCommand("reload config");
            }else {
                if (proxiedPlayer == null){
                    veloPlayer.sendMessage(Component.text(getHelp()));
                }else {
                    proxiedPlayer.sendMessage(getHelp());
                }
            }
        }
    }

    private String getHelp(){
        Messages messages = CloudAPI.getInstance().getMessages();
        String PREFIX = messages.getMessages().get("prefix").replace("&", "§");
        return PREFIX + "/cloud reload <all/config/modules>";

    }

    @Override
    public List<String> tabComplete(String[] args) {
        List<String > suggestion =  new ArrayList<>();
        if (args.length == 0){
            suggestion.add("all");
            suggestion.add("config");
            suggestion.add("modules");
        }
        return suggestion;
    }
}
