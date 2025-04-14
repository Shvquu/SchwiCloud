package eu.schwicloud.cloudplayer.offlineplayer.migrate;


import eu.schwicloud.configuration.interfaces.IConfigAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;


@Getter
@AllArgsConstructor
public class MigrateOfflinePlayerCacheConfiguration implements IConfigAdapter {

    private ArrayList<MigrateOfflinePlayer> playerCaches;

    public MigrateOfflinePlayerCacheConfiguration(){}

}
