/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud;

import eu.schwicloud.cloudflare.FlareHelper;
import eu.schwicloud.config.Configuration;
import eu.schwicloud.config.FlareGroup;
import eu.schwicloud.configuration.ConfigDriver;
import eu.schwicloud.configuration.dummys.managerconfig.ManagerConfig;
import eu.schwicloud.events.CloudFlareEvent;
import eu.schwicloud.module.extention.IModule;
import eu.roboflax.cloudflare.CloudflareAccess;

import java.io.File;
import java.util.ArrayList;


public class CloudFlareModule implements IModule {

    public static FlareHelper flareHelper;

    @Override
    public void load() {
        if (!new File("./modules/cloudflare/config.json").exists()){
            new File("./modules/cloudflare/").mkdirs();

            ArrayList<FlareGroup> groups = new ArrayList<>();
            Driver.getInstance().getGroupDriver().getAll().stream().filter(group -> group.getGroupType().equalsIgnoreCase("PROXY")).toList().forEach(group -> {
                groups.add(new FlareGroup(group.getGroup(), "@", 1, 1));
            });
            new ConfigDriver("./modules/cloudflare/config.json").save(new Configuration("me@example.com", "example.com", "your_api_token", "your_zone_id", groups));
        }else {

            ManagerConfig config = (ManagerConfig) new ConfigDriver("./service.json").read(ManagerConfig.class);
            Configuration configuration = (Configuration) new ConfigDriver("./modules/cloudflare/config.json").read(Configuration.class);
            if (!configuration.getEmail().equalsIgnoreCase("me@example.com") && !configuration.getDomain().equalsIgnoreCase("example.com") &&
                    !configuration.getApiToken().equalsIgnoreCase("your_api_token") && !configuration.getZoneID().equalsIgnoreCase("your_zone_id") && !configuration.getGroups().isEmpty()) {

                flareHelper = new FlareHelper(new CloudflareAccess(configuration.getApiToken(), configuration.getEmail()), configuration.getZoneID());
                config.getNodes().forEach(managerConfigNodes -> {
                    flareHelper.createARecord(managerConfigNodes.getName(), managerConfigNodes.getAddress());
                });
            }

            Driver.getInstance().getMessageStorage().eventDriver.registerListener(new CloudFlareEvent());
        }

        }

    @Override
    public void unload() {
        flareHelper.deleteARecords();
        flareHelper.deleteSRVRecords();
    }

    @Override
    public void reload() {
        if (!new File("./modules/cloudflare/config.json").exists()){
            new File("./modules/cloudflare/").mkdirs();

            ArrayList<FlareGroup> groups = new ArrayList<>();
            Driver.getInstance().getGroupDriver().getAll().stream().filter(group -> group.getGroupType().equalsIgnoreCase("PROXY")).toList().forEach(group -> {
                groups.add(new FlareGroup(group.getGroup(), "@", 1, 1));
            });
            new ConfigDriver("./modules/cloudflare/config.json").save(new Configuration("me@example.com", "example.com", "your_api_token", "your_zone_id", groups));
        }

        ManagerConfig config = (ManagerConfig) new ConfigDriver("./service.json").read(ManagerConfig.class);
        Configuration configuration = (Configuration) new ConfigDriver("./modules/cloudflare/config.json").read(Configuration.class);
        if (!configuration.getEmail().equalsIgnoreCase("me@example.com") && !configuration.getDomain().equalsIgnoreCase("example.com") &&
                !configuration.getApiToken().equalsIgnoreCase("your_api_token") && !configuration.getZoneID().equalsIgnoreCase("your_zone_id") && !configuration.getGroups().isEmpty()) {
            if (flareHelper == null){
                flareHelper = new FlareHelper(new CloudflareAccess(configuration.getApiToken(), configuration.getEmail()), configuration.getZoneID());
                config.getNodes().forEach(managerConfigNodes -> {
                    flareHelper.createARecord(managerConfigNodes.getName(), managerConfigNodes.getAddress());
                });
                Driver.getInstance().getMessageStorage().eventDriver.registerListener(new CloudFlareEvent());
            }

        }
    }
}
