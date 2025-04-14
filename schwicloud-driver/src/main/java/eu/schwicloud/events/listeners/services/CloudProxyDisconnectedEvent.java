package eu.schwicloud.events.listeners.services;

import eu.schwicloud.events.entrys.IEventAdapter;

public class CloudProxyDisconnectedEvent extends IEventAdapter {
    @lombok.Getter
    private final String name;

    public CloudProxyDisconnectedEvent(String name) {
        this.name = name;
    }
}
