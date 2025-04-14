package eu.schwicloud.config;

import eu.schwicloud.configuration.interfaces.IConfigAdapter;
import lombok.Getter;

import java.util.HashMap;

public class IconBase implements IConfigAdapter {

    @Getter
    private HashMap<String, String> icons;

    public IconBase() {
    }

    public IconBase(HashMap<String, String> icons) {
        this.icons = icons;
    }
}
