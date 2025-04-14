package eu.schwicloud.webserver.dummys;

import eu.schwicloud.configuration.interfaces.IConfigAdapter;

import java.util.ArrayDeque;

public class GroupList implements IConfigAdapter {

    private ArrayDeque<String> groups;

    public GroupList() {
    }

    public ArrayDeque<String> getGroups() {
        return groups;
    }

    public void setGroups(ArrayDeque<String> groups) {
        this.groups = groups;
    }
}
