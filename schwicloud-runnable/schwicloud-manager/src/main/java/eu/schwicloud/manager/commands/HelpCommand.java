package eu.schwicloud.manager.commands;

import eu.schwicloud.Driver;
import eu.schwicloud.terminal.commands.CommandAdapter;
import eu.schwicloud.terminal.commands.CommandInfo;
import eu.schwicloud.terminal.enums.Type;
import eu.schwicloud.terminal.utils.TerminalStorageLine;

import java.util.ArrayList;

@CommandInfo(command = "help", description = "command-help-description", aliases = {"?", "hilfe", "ls", "commands"})
public class HelpCommand extends CommandAdapter {


    @Override
    public void performCommand(CommandAdapter command, String[] args) {
        Driver.getInstance().getTerminalDriver().getCommandDriver().getCommands().forEach(commandAdapter -> {
            StringBuilder aliases;
            if (commandAdapter.getAliases().size() == 1){
                aliases = new StringBuilder(commandAdapter.getAliases().get(0));
            }else {
                aliases = new StringBuilder(commandAdapter.getAliases().get(0));
                for (int i = 1; i != commandAdapter.getAliases().size() ; i++) {
                    aliases.append(", ").append(commandAdapter.getAliases().get(i));
                }
            }
            Driver.getInstance().getTerminalDriver().log(Type.COMMAND, " >> §f" + commandAdapter.getCommand() + "  §7'§f"+aliases+"§7' ~ " + Driver.getInstance().getLanguageDriver().getLang().getMessage(commandAdapter.getDescription()));

        });
    }

    @Override
    public ArrayList<String> tabComplete(TerminalStorageLine consoleInput, String[] args) {
        return null;
    }
}
