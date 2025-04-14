package eu.schwicloud.webserver.dummys;

import eu.schwicloud.configuration.interfaces.IConfigAdapter;

import java.util.ArrayList;

public class Addresses implements IConfigAdapter {

    private ArrayList<String> whitelist;

    public Addresses() {
    }

    public ArrayList<String> getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(ArrayList<String> whitelist) {
        this.whitelist = whitelist;
    }
}
