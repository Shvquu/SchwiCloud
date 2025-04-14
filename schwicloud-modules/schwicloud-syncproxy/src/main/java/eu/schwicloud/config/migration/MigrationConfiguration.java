package eu.schwicloud.config.migration;

import eu.schwicloud.configuration.interfaces.IConfigAdapter;

import java.util.ArrayList;

public class MigrationConfiguration implements IConfigAdapter {

    private ArrayList<MigrationDesignConfig> configuration;

    public MigrationConfiguration() {}

    public ArrayList<MigrationDesignConfig> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ArrayList<MigrationDesignConfig> configuration) {
        this.configuration = configuration;
    }
}
