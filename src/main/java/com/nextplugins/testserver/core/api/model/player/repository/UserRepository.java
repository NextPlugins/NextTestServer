package com.nextplugins.testserver.core.api.model.player.repository;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import com.nextplugins.testserver.core.api.model.player.User;
import com.nextplugins.testserver.core.api.model.player.repository.adapter.UserAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public final class UserRepository {

    private static final String TABLE = "data_players";

    @Inject private SQLExecutor sqlExecutor;

    public void createTable() {
        sqlExecutor.updateQuery("CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                "owner CHAR(36) NOT NULL PRIMARY KEY UNIQUE," +
                "userGroup TEXT," +
                "permissions TEXT" +
                ");"
        );
    }

    public User selectOne(UUID owner) {
        return sqlExecutor.resultOneQuery(
                "SELECT * FROM " + TABLE + " WHERE owner = ?",
                statement -> statement.set(1, owner.toString()),
                UserAdapter.class
        );
    }

    public void update(User user) {

        this.sqlExecutor.updateQuery(
                String.format("REPLACE INTO %s VALUES(?,?,?)", TABLE),
                statement -> {

                    statement.set(1, user.getUniqueId().toString());
                    statement.set(2, user.getGroup().getName());
                    statement.set(3, String.join(",", user.getPermissions()));

                }
        );

    }

}
