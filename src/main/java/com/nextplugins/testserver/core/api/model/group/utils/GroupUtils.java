package com.nextplugins.testserver.core.api.model.group.utils;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.api.model.group.Group;
import com.nextplugins.testserver.core.api.model.player.User;
import com.nextplugins.testserver.core.api.model.player.storage.UserStorage;
import com.nextplugins.testserver.core.api.model.player.utils.UserUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupUtils {

    @Inject private static UserStorage userStorage;

    public static boolean addPermission(Group group, String permission) {

        if (group.getPermissions().contains(permission)) return false;

        group.getPermissions().add(permission);
        updateAttachments(group);

        return true;

    }

    public static boolean removePermission(Group group, String permission) {

        if (!group.getPermissions().contains(permission)) return false;

        group.getPermissions().remove(permission);
        updateAttachments(group);

        return true;

    }

    public static void updateAttachments(Group group) {

        try {
            for (CompletableFuture<User> player : userStorage.getOnlinePlayers()) {

                val user = player.get();
                if (user.getGroup().getSorter() != group.getSorter()) continue;

                UserUtil.updateAttachment(user);

            }
        } catch (InterruptedException | ExecutionException exception) {

            Thread.currentThread().interrupt();
            exception.printStackTrace();

        }

    }

    public static void updateAllAttachments() {
        try {
            for (CompletableFuture<User> player : userStorage.getOnlinePlayers()) {

                val user = player.get();
                UserUtil.updateAttachment(user);

            }
        } catch (InterruptedException | ExecutionException exception) {

            Thread.currentThread().interrupt();
            exception.printStackTrace();

        }
    }

}
