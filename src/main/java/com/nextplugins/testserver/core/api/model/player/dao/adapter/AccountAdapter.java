package com.nextplugins.testserver.core.api.model.player.dao.adapter;

import com.google.inject.Inject;
import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;
import com.nextplugins.testserver.core.api.model.group.Group;
import com.nextplugins.testserver.core.api.model.group.storage.GroupStorage;
import com.nextplugins.testserver.core.api.model.player.Account;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class AccountAdapter implements SQLResultAdapter<Account> {

    @Inject private static GroupStorage groupStorage;

    @Override
    public Account adaptResult(SimpleResultSet resultSet) {

        Player player = Bukkit.getPlayer(UUID.fromString(resultSet.get("owner")));

        Group group = groupStorage.getGroupByName(resultSet.get("group"));
        if (group == null) group = groupStorage.getGroupByName("Membro");

        return Account.of(player)
                .group(group)
                .wrap();

    }

}
