package com.nextplugins.testserver.core.api.model.player.repository;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import com.nextplugins.testserver.core.api.model.player.User;
import com.nextplugins.testserver.core.api.model.player.repository.adapter.UserAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class UserRepository {

    private static final String TABLE = "nextcore_data";

    @Inject private SQLExecutor sqlExecutor;

    public void createTable() {
        sqlExecutor.updateQuery("CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                "player CHAR(36) NOT NULL PRIMARY KEY UNIQUE," +
                "userGroup TEXT," +
                "permissions TEXT" +
                ");"
        );
    }

    public User selectOne(String player) {
        return sqlExecutor.resultOneQuery(
                "SELECT * FROM " + TABLE + " WHERE player = ?",
                statement -> statement.set(1, player),
                UserAdapter.class
        );
    }

    public void update(User user) {

        this.sqlExecutor.updateQuery(
                String.format("REPLACE INTO %s VALUES(?,?,?)", TABLE),
                statement -> {

                    statement.set(1, user.getName());
                    statement.set(2, user.getGroup().getName());
                    statement.set(3, String.join(",", user.getPermissions()));

                }
        );

    }

}
