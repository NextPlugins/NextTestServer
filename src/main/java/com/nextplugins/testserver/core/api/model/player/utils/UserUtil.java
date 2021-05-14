package com.nextplugins.testserver.core.api.model.player.utils;

import com.google.common.collect.Lists;
import com.nextplugins.testserver.core.api.model.group.Group;
import com.nextplugins.testserver.core.api.model.player.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

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

            if (permission.equals("*")) user.getOfflinePlayer().setOp(true);
            attachment.setPermission(permission, true);

        }

    }

}
