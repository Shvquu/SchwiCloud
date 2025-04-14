/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.manager.commands;

import eu.schwicloud.Driver;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.configuration.dummys.managerconfig.ManagerConfig;
import eu.schwicloud.manager.CloudManager;
import eu.schwicloud.networking.NettyDriver;
import eu.schwicloud.process.ServiceState;
import eu.schwicloud.terminal.commands.CommandAdapter;
import eu.schwicloud.terminal.commands.CommandInfo;
import eu.schwicloud.terminal.enums.Type;
import eu.schwicloud.terminal.utils.TerminalStorageLine;

import java.util.ArrayList;

@CommandInfo(command = "screen", description = "command-screen-description", aliases = {"terminal"})
public class ScreenCommand extends CommandAdapter {
    @Override
    public void performCommand(CommandAdapter command, String[] args) {
        if (args.length == 0) {
            sendHelp();

        }else if (args[0].equalsIgnoreCase("service")){
            if (args.length == 2){
                String service = args[1];
                if (CloudManager.serviceDriver.getService(service) != null && CloudManager.serviceDriver.getService(service).getEntry().getStatus() != ServiceState.QUEUED){
                    CloudManager.serviceDriver.getService(service).handelScreen();
                }else {
                    Driver.getInstance().getTerminalDriver().log(Type.COMMAND, Driver.getInstance().getLanguageDriver().getLang().getMessage("command-screen-service-not-found"));
                }
            }else {
                sendHelp();
            }
        }else if (args[0].equalsIgnoreCase("node")){
            if (args.length == 2){
                String node = args[1];
                if (NettyDriver.getInstance().nettyServer.isChannelFound(node)){
                    CloudManager.screenNode(node);
                }else {
                    Driver.getInstance().getTerminalDriver().log(Type.COMMAND, Driver.getInstance().getLanguageDriver().getLang().getMessage("command-screen-node-not-found"));

                }
            }else {
                sendHelp();
            }

        }else {
            sendHelp();
        }
    }

    private void sendHelp(){
        Driver.getInstance().getTerminalDriver().log(Type.COMMAND, Driver.getInstance().getLanguageDriver().getLang().getMessage("command-screen-help-1"));
        Driver.getInstance().getTerminalDriver().log(Type.COMMAND, Driver.getInstance().getLanguageDriver().getLang().getMessage("command-screen-help-2"));
    }

    @Override
    public ArrayList<String> tabComplete(TerminalStorageLine consoleInput, String[] args) {
        ArrayList<String> returns = new ArrayList<>();
        if (args.length == 0) {
            returns.add("service");
            returns.add("node");
        }else if (args[0].equalsIgnoreCase("service")){
            returns.addAll(CloudManager.serviceDriver.getServices().stream().map(taskedService -> taskedService.getEntry().getServiceName()).toList());
        }else if (args[0].equalsIgnoreCase("node")){
            ManagerConfig config = (ManagerConfig) new ConfigDriver("./service.json").read(ManagerConfig.class);
            config.getNodes().forEach(managerConfigNodes -> {if (!managerConfigNodes.getName().equalsIgnoreCase("InternalNode")) returns.add(managerConfigNodes.getName());});
        }
        return returns;
    }
}
