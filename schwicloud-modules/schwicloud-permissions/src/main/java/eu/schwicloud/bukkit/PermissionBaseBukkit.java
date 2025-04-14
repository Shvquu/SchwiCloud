/*
 * this class is by RauchigesEtwas
 */

/*
 * this class is by RauchigesEtwas
 */

/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.bukkit;

import eu.schwicloud.api.CloudPermissionAPI;
import eu.schwicloud.moduleside.config.PermissionAble;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PermissionBaseBukkit extends PermissibleBase {

    private final Player player;

    public PermissionBaseBukkit(@NotNull Player player) {
        super(player);
        this.player = player;
    }


    @Override
    public boolean hasPermission(@NotNull String inName) {
        if (player.isOp()) return true;
        ArrayList<PermissionAble> permissions = CloudPermissionAPI.getInstance().getPermissionsFromPlayer(player.getName());
        if (permissions.parallelStream().anyMatch(permissionAble -> permissionAble.getPermission().equalsIgnoreCase("*") && permissionAble.getAble())){
            return true;
        }else if (!permissions.parallelStream().filter(permissionAble -> permissionAble.getPermission().equalsIgnoreCase(inName) && permissionAble.getAble()).toList().isEmpty()){
            return true;
        }else {
            return false;
        }
    }
}

