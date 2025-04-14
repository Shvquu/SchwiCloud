package eu.schwicloud.config;

import eu.schwicloud.configuration.interfaces.IConfigAdapter;

import java.util.ArrayList;

public class Configuration implements IConfigAdapter {

    private ArrayList<DesignConfig> configuration;

    public Configuration() {}

    public ArrayList<DesignConfig> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ArrayList<DesignConfig> configuration) {
        this.configuration = configuration;
    }
}
