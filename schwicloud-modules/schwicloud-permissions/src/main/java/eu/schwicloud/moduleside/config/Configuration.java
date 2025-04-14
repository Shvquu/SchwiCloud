/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.moduleside.config;

import eu.schwicloud.configuration.interfaces.IConfigAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;


@Getter
@AllArgsConstructor
public class Configuration implements IConfigAdapter {

    private ArrayList<PermissionGroup> groups;
    private ArrayList<PermissionPlayer> players;

    public Configuration(){}

}
