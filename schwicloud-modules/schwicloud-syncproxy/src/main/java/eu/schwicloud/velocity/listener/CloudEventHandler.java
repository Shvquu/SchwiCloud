/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.velocity.listener;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.config.Configuration;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.events.entrys.ICloudListener;
import eu.schwicloud.events.entrys.Subscribe;
import eu.schwicloud.events.listeners.restapi.CloudRestAPIReloadEvent;
import eu.schwicloud.velocity.VeloCityBootstrap;

public class CloudEventHandler implements ICloudListener {

    @Subscribe
    public void handle(CloudRestAPIReloadEvent event){
        VeloCityBootstrap.getInstance().conf  = (Configuration) new ConfigDriver().convert(CloudAPI.getInstance().getRestDriver().get("/module/syncproxy/configuration"), Configuration.class);
    }

}
