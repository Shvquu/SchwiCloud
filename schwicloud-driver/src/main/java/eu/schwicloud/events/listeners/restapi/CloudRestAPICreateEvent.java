package eu.schwicloud.events.listeners.restapi;

import eu.schwicloud.events.entrys.IEventAdapter;

public class CloudRestAPICreateEvent extends IEventAdapter {

    @lombok.Getter
    private final String path;
    @lombok.Getter
    private final String content;

    public CloudRestAPICreateEvent(String path, String content) {
        this.path = path;
        this.content = content;
    }
}
