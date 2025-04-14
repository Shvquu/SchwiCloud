package eu.schwicloud.events.listeners.services;


import eu.schwicloud.events.entrys.IEventAdapter;

public class CloudServiceDisconnectedEvent extends IEventAdapter {
    @lombok.Getter
    private final String name;

    public CloudServiceDisconnectedEvent(String name) {
        this.name = name;
    }
}
