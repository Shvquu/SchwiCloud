package eu.schwicloud.node.commands;

import eu.schwicloud.Driver;
import eu.schwicloud.node.CloudNode;
import eu.schwicloud.terminal.commands.CommandAdapter;
import eu.schwicloud.terminal.commands.CommandInfo;
import eu.schwicloud.terminal.enums.Type;
import eu.schwicloud.terminal.utils.TerminalStorageLine;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


@CommandInfo(command = "stop", description = "command-stop-description", aliases = {"shutdown", "end", "quit", "kill"})
public class StopCommand extends CommandAdapter {
    @Override
    public void performCommand(CommandAdapter command, String[] args) {

        if (Driver.getInstance().getMessageStorage().shutdownAccept){
            CloudNode.shutdownHook();
        }else {
            Driver.getInstance().getMessageStorage().shutdownAccept = true;
            Driver.getInstance().getTerminalDriver().log(Type.COMMAND,Driver.getInstance().getLanguageDriver().getLang().getMessage("command-stop") );
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Driver.getInstance().getMessageStorage().shutdownAccept = false;
                }
            }, 15*1000);
        }
    }

    @Override
    public ArrayList<String> tabComplete(TerminalStorageLine consoleInput, String[] args) {
        return null;
    }
}
