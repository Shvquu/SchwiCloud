/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.moduleside.config.migrate;

import eu.schwicloud.configuration.interfaces.IConfigAdapter;
import eu.schwicloud.moduleside.config.PermissionPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;


@Getter
@AllArgsConstructor
public class MigrationConfiguration implements IConfigAdapter {

    private ArrayList<MigrationPermissionGroup> groups;
    private ArrayList<PermissionPlayer> players;

    public MigrationConfiguration(){}

}
