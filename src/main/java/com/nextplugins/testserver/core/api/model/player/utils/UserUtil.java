package com.nextplugins.testserver.core.api.model.player.utils;

import com.google.common.collect.Lists;
import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.group.Group;
import com.nextplugins.testserver.core.api.model.group.event.GroupUpdateEvent;
import com.nextplugins.testserver.core.api.model.player.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;
import org.bukkit.Bukkit;

import java.util.List;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtil {

    public static void changeGroup(User user, Group group) {

        user.setGroup(group);
        updateAttachment(user);

        val player = user.getPlayer();
        if (player == null) {
            NextTestServer.getInstance().getLogger().info("jogador off");
            return;
        }

        Bukkit.getPluginManager().callEvent(new GroupUpdateEvent(player, group));

    }

    public static void addPermission(User user, String permission) {

        user.getPermissions().add(permission);

        val attachment = user.getAttachment();
        if (attachment == null) return;

        attachment.setPermission(permission, true);

    }

    public static void removePermission(User user, String permission) {

        user.getPermissions().remove(permission);

        val attachment = user.getAttachment();
        if (attachment == null) return;

        attachment.unsetPermission(permission);

    }

    public static void updateAttachment(User user) {

        val attachment = user.getAttachment();
        if (attachment == null) return;

        attachment.getPermissions().clear();
        user.getOfflinePlayer().setOp(false);

        List<String> permissions = Lists.newArrayList(user.getGroup().getPermissions());
        permissions.addAll(user.getPermissions());

        for (String permission : permissions) {

            if (permission.equals("*")) {
                user.getOfflinePlayer().setOp(true);
                break;
            }

            attachment.setPermission(permission, !permission.startsWith("-"));

        }

    }

}
