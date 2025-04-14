package eu.schwicloud.events.listeners.group;

import eu.schwicloud.events.entrys.IEventAdapter;

public class CloudGroupDeleteEvent extends IEventAdapter {

    @lombok.Getter
    private final String groupname;

    public CloudGroupDeleteEvent(String groupname) {
        this.groupname = groupname;
    }

}
