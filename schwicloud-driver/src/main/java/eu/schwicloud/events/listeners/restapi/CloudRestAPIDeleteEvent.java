package eu.schwicloud.events.listeners.restapi;

import eu.schwicloud.events.entrys.IEventAdapter;

public class CloudRestAPIDeleteEvent extends IEventAdapter {
    @lombok.Getter
    private final String path;

    public CloudRestAPIDeleteEvent(String path) {
        this.path = path;
    }
}
