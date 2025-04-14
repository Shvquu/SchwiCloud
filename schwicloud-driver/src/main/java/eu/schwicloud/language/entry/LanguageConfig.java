package eu.schwicloud.language.entry;

import eu.schwicloud.configuration.interfaces.IConfigAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;


@Getter
@AllArgsConstructor
public class LanguageConfig implements IConfigAdapter {

    private HashMap<String, String> messages;

    public LanguageConfig() {}
}
