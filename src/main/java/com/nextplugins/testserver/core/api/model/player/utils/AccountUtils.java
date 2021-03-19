package com.nextplugins.testserver.core.api.model.player.utils;

import com.google.common.collect.Lists;
import com.nextplugins.testserver.core.api.model.group.Group;
import com.nextplugins.testserver.core.api.model.player.Account;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountUtils {

    public static void changeGroup(Account account, Group group) {

        account.setGroup(group);
        updateAttachment(account);

    }

    public static void addPermission(Account account, String permission) {

        account.getPermissions().add(permission);
        account.getAttachment().setPermission(permission, true);

    }

    public static void removePermission(Account account, String permission) {

        account.getPermissions().remove(permission);
        account.getAttachment().unsetPermission(permission);

    }

    public static void updateAttachment(Account account) {

        account.getAttachment().getPermissions().clear();

        List<String> permissions = Lists.newArrayList(account.getGroup().getPermissions());
        permissions.addAll(account.getPermissions());

        for (String permission : permissions) {
            account.getAttachment().setPermission(permission, true);
        }

    }

}
