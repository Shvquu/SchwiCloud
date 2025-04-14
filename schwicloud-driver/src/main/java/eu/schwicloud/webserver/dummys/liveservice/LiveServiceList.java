package eu.schwicloud.webserver.dummys.liveservice;

import eu.schwicloud.configuration.interfaces.IConfigAdapter;

import java.util.ArrayDeque;

public class LiveServiceList implements IConfigAdapter {


    private String cloudServiceSplitter;
    private ArrayDeque<String> cloudServices;

    public LiveServiceList() {}

    public String getCloudServiceSplitter() {
        return cloudServiceSplitter;
    }

    public ArrayDeque<String> getCloudServices() {
        return cloudServices;
    }

    public void  remove(String service){
        cloudServices.removeIf(s -> s.equalsIgnoreCase(service));
    }

    public void setCloudServiceSplitter(String cloudServiceSplitter) {
        this.cloudServiceSplitter = cloudServiceSplitter;
    }

    public void setCloudServices(ArrayDeque<String> cloudServices) {
        this.cloudServices = cloudServices;
    }
}
