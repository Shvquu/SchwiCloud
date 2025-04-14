package eu.schwicloud.events.listeners.player;


import eu.schwicloud.events.entrys.IEventAdapter;

import java.util.UUID;

public class CloudPlayerConnectedEvent extends IEventAdapter {
    @lombok.Getter
    private final String name;

    @lombok.Getter
    private final String proxy;

    @lombok.Getter
    private final UUID UniqueId;

    public CloudPlayerConnectedEvent(String name, String proxy, UUID UniqueId) {
        this.name = name;
        this.proxy = proxy;
        this.UniqueId = UniqueId;
    }
}
