package eu.schwicloud.api;

import eu.schwicloud.CloudAPI;
import eu.schwicloud.config.Configuration;
import eu.schwicloud.config.Locations;
import eu.schwicloud.config.SignLocation;
import eu.schwicloud.configuration.ConfigDriver;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.util.ArrayList;

public class SignsAPI {

    public Configuration configuration;
     public Locations Locations;

    public SignsAPI() {

        if (!new File("./service.json").exists()){
            configuration = ((Configuration) new ConfigDriver().convert( CloudAPI.getInstance().getRestDriver().get("/module/signs/configuration"), Configuration.class));
            Locations = ((Locations) new ConfigDriver().convert( CloudAPI.getInstance().getRestDriver().get("/module/signs/locations"), Locations.class));
        }
    }

    public ArrayList<SignLocation> getSigns(){
       return getLocConfig().getLocations();
    }

    public ArrayList<String> getUUIDs(){
        ArrayList<String> uuids = new ArrayList<>();
        getLocConfig().getLocations().forEach(signLocation -> {
            uuids.add(signLocation.getSignUUID());
        });
        return uuids;
    }

    public boolean canFind(Location location){
        return getLocations().stream().anyMatch(location1 -> location1.getX() == location.getX() && location1.getY() == location.getY() && location1.getZ() == location.getZ() && location1.getWorld().getName().equalsIgnoreCase(location.getWorld().getName()));
    }

    public ArrayList<Location> getLocations(){
        ArrayList<Location> uuids = new ArrayList<>();
        getLocConfig().getLocations().forEach(signLocation -> {
            uuids.add(new Location(Bukkit.getWorld(signLocation.getLocationWorld()), signLocation.getLocationPosX(),signLocation.getLocationPosY(), signLocation.getLocationPosZ()));
        });
        return uuids;
    }

    public Locations getLocConfig(){
        return this.Locations;
    }
    public Configuration getConfig(){
        return this.configuration;
    }

    public void createSign(SignLocation location){
        if (getSigns().stream().noneMatch(location1 -> location1.getSignUUID().equals(location.getSignUUID()))){

            Locations l =  Locations;
            l.getLocations().add(location);
            Locations = l;
            CloudAPI.getInstance().getRestDriver().update("/module/signs/locations", new ConfigDriver().convert(l));
        }
    }

    public void removeSign(String  signUUID){
        if (getSigns().stream().anyMatch(location1 -> location1.getSignUUID().equals(signUUID))){
            ArrayList<SignLocation> update = getSigns();
            Locations l =  Locations;
            l.getLocations().removeIf(location -> location.getSignUUID().equals(signUUID));
            Locations = l;
            CloudAPI.getInstance().getRestDriver().update("/module/signs/locations", new ConfigDriver().convert(l));
        }
    }
}
