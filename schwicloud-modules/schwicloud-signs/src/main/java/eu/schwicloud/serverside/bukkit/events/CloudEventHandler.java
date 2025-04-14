/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.serverside.bukkit.events;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.config.Configuration;
import eu.schwicloud.config.Locations;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.events.entrys.ICloudListener;
import eu.schwicloud.events.entrys.Subscribe;
import eu.schwicloud.events.listeners.restapi.CloudRestAPIReloadEvent;
import eu.schwicloud.serverside.bukkit.SignBootstrap;

public class CloudEventHandler implements ICloudListener {

    @Subscribe
    public void handle(CloudRestAPIReloadEvent e){
        SignBootstrap.signsAPI.configuration = ((Configuration) new ConfigDriver().convert( CloudAPI.getInstance().getRestDriver().get("/module/signs/configuration"), Configuration.class));
        SignBootstrap.signsAPI.Locations = ((Locations) new ConfigDriver().convert( CloudAPI.getInstance().getRestDriver().get("/module/signs/locations"), Locations.class));

    }
}
