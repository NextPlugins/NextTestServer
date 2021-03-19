package com.nextplugins.testserver.core.api.model.player.dao.adapter;

import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;
import com.nextplugins.testserver.core.api.model.group.Group;
import com.nextplugins.testserver.core.api.model.player.Account;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class AccountAdapter implements SQLResultAdapter<Account> {

    @Override
    public Account adaptResult(SimpleResultSet resultSet) {

        Player player = Bukkit.getPlayer(UUID.fromString(resultSet.get("owner")));

        return Account.of(player)
                .group(Group.valueOf(resultSet.get("group")))
                .wrap();

    }

}
