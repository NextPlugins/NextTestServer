package com.nextplugins.testserver.core.api.model.player.storage;

import com.nextplugins.testserver.core.api.model.player.Account;
import com.nextplugins.testserver.core.api.model.player.dao.AccountDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Singleton
public final class AccountStorage {

    private final Map<UUID, Account> players = new HashMap<>();

    @Inject private AccountDAO accountDAO;

    public void init() {
        this.accountDAO.createTable();
    }

    public void insertData(Account account) {

        this.players.put(account.getPlayer().getUniqueId(), account);
        saveData(account);

    }

    public void purgeData(Account account) {

        this.players.put(account.getPlayer().getUniqueId(), account);
        saveData(account);

    }

    private void saveData(Account account) {
        this.accountDAO.update(account);
    }

    public Collection<Account> getOnlinePlayers() {
        return players.values();
    }

}
