package eu.schwicloud.cloudplayer.offlineplayer.ceched;


import eu.schwicloud.configuration.interfaces.IConfigAdapter;
import lombok.*;

import java.util.ArrayList;


@Getter
@AllArgsConstructor
public class OfflinePlayerCacheConfiguration implements IConfigAdapter {

    private ArrayList<OfflinePlayerCache> playerCaches;

    public OfflinePlayerCacheConfiguration(){}

}
