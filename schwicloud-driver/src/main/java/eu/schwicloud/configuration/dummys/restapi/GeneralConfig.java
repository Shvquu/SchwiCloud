package eu.schwicloud.configuration.dummys.restapi;

import eu.schwicloud.configuration.interfaces.IConfigAdapter;
import lombok.Getter;

import java.util.HashMap;

public class GeneralConfig implements IConfigAdapter {


    @Getter
    private HashMap<String, String> config = new HashMap<>();


    public GeneralConfig(){

    }

}
