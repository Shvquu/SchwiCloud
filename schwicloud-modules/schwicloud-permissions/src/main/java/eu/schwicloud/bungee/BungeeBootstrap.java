/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.bungee;

import eu.schwicloud.api.CloudPermissionAPI;
import eu.schwicloud.api.PluginDriver;
import eu.schwicloud.bungee.listener.PermissionListener;
import eu.schwicloud.subcommand.PermissionCommand;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeBootstrap extends Plugin {


    @Override
    public void onLoad() {
        new CloudPermissionAPI();
    }

    @Override
    public void onEnable() {

        ProxyServer.getInstance().getPluginManager().registerListener(this, new PermissionListener());
        PluginDriver.getInstance().register(new PermissionCommand());
    }
}
