package eu.schwicloud.config;

import eu.schwicloud.configuration.interfaces.IConfigAdapter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Configuration implements IConfigAdapter {

    private String discordToken;
    private String discordGuild;
    private String channelID;
    private String footer;
    private String logo;
    private ActivityConfiguration activity;


}
