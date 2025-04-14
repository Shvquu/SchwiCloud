package eu.schwicloud.events.listeners.services;

import eu.schwicloud.events.entrys.IEventAdapter;

public class CloudServiceCouldNotStartEvent extends IEventAdapter {

    @lombok.Getter
    private final String name;

    public CloudServiceCouldNotStartEvent(String name) {
        this.name = name;
    }
}
