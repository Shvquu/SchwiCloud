package eu.schwicloud.webserver.dummys;

import eu.schwicloud.configuration.interfaces.IConfigAdapter;

import java.util.ArrayList;

public class PlayerGeneral implements IConfigAdapter {

    private ArrayList<String> cloudplayers;

    public ArrayList<String> getCloudplayers() {
        return cloudplayers;
    }

    public void setCloudplayers(ArrayList<String> cloudplayers) {
        this.cloudplayers = cloudplayers;
    }
}
