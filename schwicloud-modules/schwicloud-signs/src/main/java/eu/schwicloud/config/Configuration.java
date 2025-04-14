package eu.schwicloud.config;

import eu.schwicloud.configuration.interfaces.IConfigAdapter;

import java.util.ArrayList;

public class Configuration implements IConfigAdapter {


    public  ArrayList<SignConfig> configurations;
    public Configuration() {
    }

    public Configuration(ArrayList<SignConfig> configurations) {
        this.configurations = configurations;
    }
}
