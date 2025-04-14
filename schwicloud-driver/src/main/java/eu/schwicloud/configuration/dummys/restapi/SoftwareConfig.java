package eu.schwicloud.configuration.dummys.restapi;

import eu.schwicloud.configuration.interfaces.IConfigAdapter;
import java.util.HashMap;

public class SoftwareConfig implements IConfigAdapter {
    @lombok.Setter
    @lombok.Getter
    private HashMap<String, String> spigots;
    @lombok.Setter
    @lombok.Getter
    private HashMap<String, String> bungeecords;


    public SoftwareConfig(){

    }

}
