package com.nextplugins.testserver.core.api.model.player.repository.adapter;

import com.google.inject.Inject;
import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;
import com.nextplugins.testserver.core.api.model.group.Group;
import com.nextplugins.testserver.core.api.model.group.storage.GroupStorage;
import com.nextplugins.testserver.core.api.model.player.User;
import com.nextplugins.testserver.core.api.model.player.utils.UserUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;
import java.util.UUID;

public final class UserAdapter implements SQLResultAdapter<User> {

    @Inject private static GroupStorage groupStorage;

    @Override
    public User adaptResult(SimpleResultSet resultSet) {

        String playerName = resultSet.get("player");
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        Group group = groupStorage.getGroupByName(resultSet.get("userGroup"));
        if (group == null) group = groupStorage.getDefaultGroup();

        val accountBuilder = player.isOnline()
                ? User.createDefault(player.getPlayer())
                : User.createDefault(player);

        String permissions = resultSet.get("permissions");
        val user = permissions.equals("")
                ? accountBuilder.permissions(Arrays.asList(permissions.split(","))).wrap()
                : accountBuilder.wrap();

        UserUtil.changeGroup(user, group);

        return user;

    }

}
