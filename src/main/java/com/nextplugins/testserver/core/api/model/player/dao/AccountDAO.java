package com.nextplugins.testserver.core.api.model.player.dao;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import com.nextplugins.testserver.core.api.model.player.Account;
import com.nextplugins.testserver.core.api.model.player.dao.adapter.AccountAdapter;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public final class AccountDAO {

    private final String TABLE = "data_players";

    private final SQLExecutor sqlExecutor;

    public void createTable() {
        sqlExecutor.updateQuery("CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                "owner CHAR(36) NOT NULL PRIMARY KEY," +
                "group TEXT NOT NULL" +
                ");"
        );
    }

    public Account selectOne(UUID owner) {
        return sqlExecutor.resultOneQuery(
                "SELECT * FROM " + TABLE + " WHERE owner = ?",
                statement -> statement.set(1, owner.toString()),
                AccountAdapter.class
        );
    }

    public Set<Account> selectAll(String query) {
        return sqlExecutor.resultManyQuery(
                "SELECT * FROM " + TABLE + " " + query,
                k -> {
                },
                AccountAdapter.class
        );
    }

    public void update(Account account) {

        this.sqlExecutor.updateQuery(
                String.format("REPLACE INTO %s VALUES(?,?)", TABLE),
                statement -> {

                    statement.set(1, account.getPlayer().getUniqueId().toString());
                    statement.set(2, account.getGroup().toString());

                }
        );

    }

}
