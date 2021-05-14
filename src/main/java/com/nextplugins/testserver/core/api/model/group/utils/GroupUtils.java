package com.nextplugins.testserver.core.api.model.group.utils;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.api.model.group.Group;
import com.nextplugins.testserver.core.api.model.player.storage.AccountStorage;
import com.nextplugins.testserver.core.api.model.player.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupUtils {

    @Inject private static AccountStorage accountStorage;

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

        accountStorage.getOnlinePlayers()
                .stream()
                .filter(account -> account.getGroup().getName().equals(group.getName()))
                .forEach(AccountUtils::updateAttachment);

    }

    public static void updateAllAttachments() {
        accountStorage.getOnlinePlayers().forEach(AccountUtils::updateAttachment);
    }

}
