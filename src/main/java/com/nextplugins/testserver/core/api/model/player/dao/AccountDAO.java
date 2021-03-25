package com.nextplugins.testserver.core.api.model.player.dao;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import com.nextplugins.testserver.core.api.model.player.Account;
import com.nextplugins.testserver.core.api.model.player.dao.adapter.AccountAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public final class AccountDAO {

    private static final String TABLE = "data_players";

    @Inject private SQLExecutor sqlExecutor;

    public void createTable() {
        sqlExecutor.updateQuery("CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                "owner CHAR(36) NOT NULL PRIMARY KEY," +
                "group TEXT" +
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

    public void update(Account account) {

        this.sqlExecutor.updateQuery(
                String.format("REPLACE INTO %s VALUES(?,?)", TABLE),
                statement -> {

                    statement.set(1, account.getPlayer().getUniqueId().toString());
                    statement.set(2, account.getGroup().getName());

                }
        );

    }

}
