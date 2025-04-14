package eu.schwicloud.config;

import eu.schwicloud.configuration.interfaces.IConfigAdapter;

import java.util.ArrayList;

public class Locations implements IConfigAdapter {

    ArrayList<SignLocation> locations;

    public Locations() {
    }

    public Locations(ArrayList<SignLocation> locations) {
        this.locations = locations;
    }

    public ArrayList<SignLocation> getLocations() {
        return locations;
    }
}
