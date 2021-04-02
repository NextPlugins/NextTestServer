package com.nextplugins.testserver.core.api.model.player.storage;

import com.nextplugins.testserver.core.api.model.player.Account;
import com.nextplugins.testserver.core.api.model.player.dao.AccountDAO;
import org.bukkit.OfflinePlayer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    public Account from(OfflinePlayer player) {

        Account account = players.getOrDefault(player.getUniqueId(), null);
        if (account == null) {

            account = accountDAO.selectOne(player.getUniqueId());
            if (account == null && player.isOnline()) {

                account = Account.createDefault(player.getPlayer()).wrap();
                accountDAO.update(account);

                this.players.put(player.getUniqueId(), account);

            }

        }

        return account;

    }

    public void unload() {
        getOnlinePlayers().forEach(this::purgeData);
    }

    public void purgeData(Account account) {
        this.players.remove(account.getUniqueId());
        this.accountDAO.update(account);
    }

    public Collection<Account> getOnlinePlayers() {
        return players.values();
    }

}
