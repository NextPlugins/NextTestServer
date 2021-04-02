package com.nextplugins.testserver.core.api.model.player.dao.adapter;

import com.google.inject.Inject;
import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;
import com.nextplugins.testserver.core.api.model.group.Group;
import com.nextplugins.testserver.core.api.model.group.storage.GroupStorage;
import com.nextplugins.testserver.core.api.model.player.Account;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

public final class AccountAdapter implements SQLResultAdapter<Account> {

    @Inject private static GroupStorage groupStorage;

    @Override
    public Account adaptResult(SimpleResultSet resultSet) {

        OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(resultSet.get("owner")));

        Group group = groupStorage.getGroupByName(resultSet.get("userGroup"));
        if (group == null) group = groupStorage.getGroupByName("Membro");

        val accountBuilder = player.isOnline()
                ? Account.createDefault(player.getPlayer())
                : Account.createDefault(player)
                .group(group);

        String permissions = resultSet.get("permissions");
        if (permissions.equalsIgnoreCase("")) return accountBuilder.wrap();

        return accountBuilder.permissions(Arrays.asList(permissions.split(",")))
                .wrap();

    }

}
