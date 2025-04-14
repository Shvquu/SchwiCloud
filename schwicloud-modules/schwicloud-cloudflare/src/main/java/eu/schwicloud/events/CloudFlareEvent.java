/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.events;

import eu.schwicloud.CloudFlareModule;
import eu.schwicloud.config.Configuration;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.events.entrys.ICloudListener;
import eu.schwicloud.events.entrys.Subscribe;
import eu.schwicloud.events.listeners.services.CloudProxyConnectedEvent;
import eu.schwicloud.events.listeners.services.CloudProxyDisconnectedEvent;

public class CloudFlareEvent implements ICloudListener {

    @Subscribe
    public void register(CloudProxyConnectedEvent event){
        Configuration configuration = (Configuration) new ConfigDriver("./modules/cloudflare/config.json").read(Configuration.class);
        if (!configuration.getEmail().equalsIgnoreCase("me@example.com") && !configuration.getDomain().equalsIgnoreCase("example.com") &&
                !configuration.getApiToken().equalsIgnoreCase("your_api_token") && !configuration.getZoneID().equalsIgnoreCase("your_zone_id") && !configuration.getGroups().isEmpty()) {
            CloudFlareModule.flareHelper.createSRVRecord(event.getName(), event.getGroup(), event.getPort(), event.getNode());
        }
    }

    @Subscribe
    public void unregister(CloudProxyDisconnectedEvent event){
        Configuration configuration = (Configuration) new ConfigDriver("./modules/cloudflare/config.json").read(Configuration.class);
        if (!configuration.getEmail().equalsIgnoreCase("me@example.com") && !configuration.getDomain().equalsIgnoreCase("example.com") &&
                !configuration.getApiToken().equalsIgnoreCase("your_api_token") && !configuration.getZoneID().equalsIgnoreCase("your_zone_id") && !configuration.getGroups().isEmpty()) {

            CloudFlareModule.flareHelper.deleteSRVRecord(event.getName());
        }
    }

}
