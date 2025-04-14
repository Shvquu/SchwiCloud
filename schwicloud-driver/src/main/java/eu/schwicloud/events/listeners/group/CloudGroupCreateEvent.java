package eu.schwicloud.events.listeners.group;

import eu.schwicloud.events.entrys.IEventAdapter;

public class CloudGroupCreateEvent extends IEventAdapter {

    @lombok.Getter
    private final String groupname;

    public CloudGroupCreateEvent(String groupname) {
        this.groupname = groupname;
    }

}
