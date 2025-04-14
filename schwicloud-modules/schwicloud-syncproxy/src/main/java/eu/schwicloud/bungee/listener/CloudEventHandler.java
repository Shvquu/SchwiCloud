/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.bungee.listener;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.bungee.BungeeBootstrap;
import eu.schwicloud.config.Configuration;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.events.entrys.ICloudListener;
import eu.schwicloud.events.entrys.Subscribe;
import eu.schwicloud.events.listeners.restapi.CloudRestAPIReloadEvent;

public class CloudEventHandler implements ICloudListener {

    @Subscribe
    public void handle(CloudRestAPIReloadEvent e){
        BungeeBootstrap.getInstance().conf = (Configuration) new ConfigDriver().convert(CloudAPI.getInstance().getRestDriver().get("/module/syncproxy/configuration"), Configuration.class);

        BungeeBootstrap.getInstance().configuration =   BungeeBootstrap.getInstance().conf.getConfiguration().stream().filter(designConfig -> designConfig.getTargetGroup().equalsIgnoreCase(CloudAPI.getInstance().getCurrentService().getGroup())).findFirst().get();
           }

}
